package com.ambroo.Server;

import io.javalin.Javalin;
import org.json.JSONObject;

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
    private static boolean active;
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
        setPort(5501);
        app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> it.anyHost());
            });
        });
        registerRoutes();
        app.start(port);
    }

    private void registerRoutes() {
        app.post("/test", ctx -> {
            String question = ctx.body();
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
                active = false;
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
            active = obj.optBoolean("active", false);
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
            obj.put("active", isActive());
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

    public static boolean isActive() { return active; }
    public static void setActive(boolean value) { active = value; saveConfig(); }
    public static int getPort() { return port; }
    public static void setPort(int value) { port = value; saveConfig(); }
    public static int getFilesAmount() { return filesAmount; }
    public static void setFilesAmount(int value) { filesAmount = value; saveConfig(); }
    public static String getServerPath() { return serverPath; }
    public static void setServerPath(String value) { serverPath = value; saveConfig(); }
    public static String getPassword() { return password; }
    public static void setPassword(String value) { password = value; saveConfig(); }
    public static boolean isPswdToDownload() { return pswdToDownload; }
    public static void setPswdToDownload(boolean value) { pswdToDownload = value; saveConfig(); }
    public static boolean isPswdToUpload() { return pswdToUpload; }
    public static void setPswdToUpload(boolean value) { pswdToUpload = value; saveConfig(); }
}
