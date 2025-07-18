package com.ambroo.Server;

import io.javalin.Javalin;
import org.json.JSONObject;

import com.ambroo.Main;
import com.ambroo.Panels.ConnectedDevicesPanel;
import com.ambroo.Panels.ServerStatusPanel;

import java.awt.Color;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;

public class Server {
    public static Javalin app;
    private static boolean autostart;
    private static int port;
    private static int filesAmount;
    private static String serverPath;
    private static String password;
    private static boolean requireNickname;
    private static boolean requirePassword;

    static {
        loadConfig();
    }

    public Server() {
    }

    public static synchronized void startServer() throws Exception {
        if (app != null) {
            try {
                app.start("0.0.0.0", port);
                return;
            } catch (io.javalin.util.JavalinException ex) {
                app = null;
            }
        }
        app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> it.anyHost());
            });
            // Serve static files from the correct React app's dist directory (absolute path)
            config.staticFiles.add(
                Paths.get("src", "main", "resources", "local_ftp_client", "dist").toString(),
                io.javalin.http.staticfiles.Location.EXTERNAL
            );
        });
        registerRoutes();
        app.start("0.0.0.0", port);
    }

    public static void registerRoutes() {
        if (app == null)
            return;
        app.post("/test", ctx -> {
            // fetch('/test', {
            // method: 'POST',
            // headers: {
            // 'Content-Type': 'text/plain'
            // }
            // body: "example"
            // })
            // .then(res => res.text())
            // .then(console.log)
            // .catch(console.error);
            String question = ctx.body();
            Main.logger.info("Request received from endpoint " + getSocket() + "test ==> " + question);
            ctx.result("Received " + question);
        });
        app.post("/add-device", ctx -> {
            // Example JS function to call this API:
            // fetch('/add-device', {
            // method: 'POST',
            // headers: {
            // 'Content-Type': 'application/json'
            // },
            // body: JSON.stringify({ 'device-name': deviceName })
            // })
            // .then(res => res.text())
            // .then(console.log)
            // .catch(console.error);
            JSONObject requestBody = new JSONObject(ctx.body());
            String deviceName = requestBody.getString("device-name");
            String ipAddress = ctx.ip();
            String randomID;
            randomID = generateRandomId();
            Main.logger.info("Device ID: " + randomID);
            ctx.result(randomID);
            Main.logger.info("Device " + deviceName + " connected from " + ipAddress);
            ConnectedDevicesPanel.addDevice(deviceName, ipAddress);
            ctx.header("Access-Control-Expose-Headers", "unique-id");
            ctx.header("unique-id", randomID);
            ctx.status(200);
        });
    }

    private static String generateRandomId() {
        String characters = "ABCDEF0123456789";
        StringBuilder randomId = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            int index = (int) (Math.random() * characters.length());
            randomId.append(characters.charAt(index));
            if ((i + 1) % 5 == 0 && i != 19) {
                randomId.append("-");
            }
        }
        return randomId.toString();
    }

    public static void loadConfig() {
        try {
            Path configPath = Paths.get("config.json");
            if (!Files.exists(configPath)) {
                serverPath = "";
                password = "";
                requireNickname = false;
                requirePassword = false;
                autostart = false;
                port = 5501;
                filesAmount = 0;
                saveConfig();
                return;
            }
            String json = Files.readString(configPath, StandardCharsets.UTF_8);
            JSONObject obj = new JSONObject(json);
            serverPath = obj.optString("serverPath", "");
            password = obj.optString("psswd", "");
            requireNickname = obj.optBoolean("requireNickname", false);
            requirePassword = obj.optBoolean("requirePassword", false);
            autostart = obj.optBoolean("autostart", false);
            port = obj.optInt("port", 5501);
            filesAmount = obj.optInt("filesAmount", 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveConfig() {
        try {
            Path configPath = Paths.get("config.json");
            JSONObject obj = new JSONObject();
            obj.put("serverPath", getServerPath());
            obj.put("psswd", getPassword());
            obj.put("requireNickname", isRequireNickname());
            obj.put("requirePassword", isRequirePassword());
            obj.put("autostart", isAutostart());
            obj.put("port", getPort());
            obj.put("filesAmount", getFilesAmount());
            Files.writeString(configPath, obj.toString(4));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getIp() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp())
                    continue;
                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (addr.isSiteLocalAddress()) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "0.0.0.0";
    }

    public static String getSocket() {
        return "http://" + getIp() + ":" + getPort() + "/";
    }

    public static boolean start() {
        setServerStarting();
        new Thread(() -> {
            try {
                Server.startServer();
                javax.swing.SwingUtilities.invokeLater(() -> {
                    ServerStatusPanel.setServerOnline(true);
                });
            } catch (Exception ex) {
                ex.printStackTrace();
                javax.swing.SwingUtilities.invokeLater(() -> {
                    ServerStatusPanel.setServerOnline(false);
                });
            }
        }).start();
        return true;
    }

    public static boolean stop() {
        if (app != null) {
            try {
                app.stop();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            app = null;
        }
        ServerStatusPanel.setServerOnline(false);
        return true;
    }

    private static void setServerStarting() {
        ServerStatusPanel.serverStatusLabel.setText("Status: Starting");
        ServerStatusPanel.serverStatusLabel.setBackground(new Color(204, 119, 34));
        ServerStatusPanel.startServerBtn.setEnabled(false);
        ServerStatusPanel.stopServerBtn.setEnabled(false);
    }

    public static boolean isAutostart() {
        return autostart;
    }

    public static void setAutostart(boolean value) {
        autostart = value;
        saveConfig();
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int value) {
        port = value;
        saveConfig();
    }

    public static int getFilesAmount() {
        return filesAmount;
    }

    public static void setFilesAmount(int value) {
        filesAmount = value;
        saveConfig();
    }

    public static String getServerPath() {
        return serverPath;
    }

    public static void setServerPath(String value) {
        serverPath = value;
        saveConfig();
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String value) {
        password = value;
        saveConfig();
    }

    public static boolean isRequireNickname() {
        return requireNickname;
    }

    public static void setRequireNickname(boolean value) {
        requireNickname = value;
        saveConfig();
    }

    public static boolean isRequirePassword() {
        return requirePassword;
    }

    public static void setRequirePassword(boolean value) {
        requirePassword = value;
        saveConfig();
    }
}
