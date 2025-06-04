package com.ambroo.Server;

import io.javalin.Javalin;
import org.json.JSONObject;

import com.ambroo.Main;
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
    private static boolean pswdToDownload;
    private static boolean pswdToUpload;

    static {
        loadConfig();
    }

    public Server() {
    }

    public static synchronized void startServer() throws Exception {
        if (app != null) {
            try {
                app.start(port);
                return;
            } catch (io.javalin.util.JavalinException ex) {
                app = null;
            }
        }
        app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> it.anyHost());
            });
        });
        registerRoutes();
        app.start(port);
    }

    public static void registerRoutes() {
        if (app == null)
            return;
        app.post("/test", ctx -> {
            String question = ctx.body();
            Main.logger.info("Request received from endpoint " + getSocket() + "test ==> " + question);
            ctx.result("Received " + question);
        });
    }

    public static void loadConfig() {
        try {
            Path configPath = Paths.get("config.json");
            if (!Files.exists(configPath)) {
                serverPath = "";
                password = "";
                pswdToDownload = false;
                pswdToUpload = false;
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
            pswdToDownload = obj.optBoolean("psswd_to_download", false);
            pswdToUpload = obj.optBoolean("psswd_to_upload", false);
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
            obj.put("psswd_to_download", isPswdToDownload());
            obj.put("psswd_to_upload", isPswdToUpload());
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

    public static boolean isPswdToDownload() {
        return pswdToDownload;
    }

    public static void setPswdToDownload(boolean value) {
        pswdToDownload = value;
        saveConfig();
    }

    public static boolean isPswdToUpload() {
        return pswdToUpload;
    }

    public static void setPswdToUpload(boolean value) {
        pswdToUpload = value;
        saveConfig();
    }
}
