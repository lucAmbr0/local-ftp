package com.ambroo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.json.JSONObject;

public class Data {
    private static final PropertyChangeSupport pcs = new PropertyChangeSupport(Data.class);

    private static String serverPath;
    private static boolean psswd_to_download;
    private static boolean psswd_to_upload;
    private static String psswd;

    static {
        loadConfig();
    }

    public static void loadConfig() {
        try (InputStream is = Data.class.getResourceAsStream("/config.json")) {
            if (is != null) {
                String json = new Scanner(is, StandardCharsets.UTF_8).useDelimiter("\\A").next();
                JSONObject obj = new JSONObject(json);
                serverPath = obj.optString("serverPath", "");
                psswd_to_download = obj.optBoolean("psswd_to_download", false);
                psswd_to_upload = obj.optBoolean("psswd_to_upload", false);
                psswd = obj.optString("psswd", "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveConfig() {
        try {
            java.nio.file.Path configPath = java.nio.file.Paths.get("d:/lucaa/Programmazione/local-ftp/local_ftp_server/src/main/resources/config.json");
            org.json.JSONObject obj = new org.json.JSONObject();
            obj.put("serverPath", getServerPath());
            obj.put("psswd_to_download", getPsswdToDownload());
            obj.put("psswd_to_upload", getPsswdToUpload());
            obj.put("psswd", getPsswd());
            java.nio.file.Files.writeString(configPath, obj.toString(4));
        } catch (Exception ex) {
            Main.logger.error("Failed to save config.json: " + ex.getMessage());
        }
    }

    public static String getServerPath() { return serverPath; }
    public static void setServerPath(String value) {
        String old = serverPath;
        serverPath = value;
        pcs.firePropertyChange("serverPath", old, value);
    }
    public static boolean getPsswdToDownload() { return psswd_to_download; }
    public static void setPsswdToDownload(boolean value) {
        boolean old = psswd_to_download;
        psswd_to_download = value;
        pcs.firePropertyChange("psswd_to_download", old, value);
        saveConfig();
    }
    public static boolean getPsswdToUpload() { return psswd_to_upload; }
    public static void setPsswdToUpload(boolean value) {
        boolean old = psswd_to_upload;
        psswd_to_upload = value;
        pcs.firePropertyChange("psswd_to_upload", old, value);
        saveConfig();
    }
    public static String getPsswd() { return psswd; }
    public static void setPsswd(String value) {
        String old = psswd;
        psswd = value;
        pcs.firePropertyChange("psswd", old, value);
        saveConfig();
    }

    public static void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    public static void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
}
