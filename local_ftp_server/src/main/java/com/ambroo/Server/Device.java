package com.ambroo.Server;

import java.util.ArrayList;

public class Device {
    
    private static int connectedDevices;
    private static int savedDevices;

    private static ArrayList<Device> devices;

    private String name;
    private String ip;
    private String uniqueId;
    private int level;
    private boolean connected;

    /**
     * @param name
     * @param ip
     * @param uniqueId
     * @param level
     * @param connected
     */
    public Device(String name, String ip, String uniqueId, int level, boolean connected) {
        this.name = name;
        this.ip = ip;
        this.uniqueId = uniqueId;
        this.level = level;
        this.connected = connected;
        if(devices == null){
            devices = new ArrayList<Device>();
        }

        boolean found = false;
        for (Device device : devices) {
            if(device.getUniqueId().equals(this.uniqueId)){
            found = true;
            break;
            }
        }

        if(!found){
            devices.add(this);
            savedDevices++;
        }
    }

    /**
     * @return the connectedDevices
     */
    public static int getConnectedDevices() {
        return connectedDevices;
    }

    /**
     * @param connectedDevices the connectedDevices to set
     */
    public static void setConnectedDevices(int connectedDevices) {
        Device.connectedDevices = connectedDevices;
    }

    /**
     * @return the savedDevices
     */
    public static int getSavedDevices() {
        return savedDevices;
    }

    /**
     * @param savedDevices the savedDevices to set
     */
    public static void setSavedDevices(int savedDevices) {
        Device.savedDevices = savedDevices;
    }

    /**
     * @return the devices
     */
    public static ArrayList<Device> getDevices() {
        return devices;
    }

    /**
     * @param devices the devices to set
     */
    public static void setDevices(ArrayList<Device> devices) {
        Device.devices = devices;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the uniqueId
     */
    public String getUniqueId() {
        return uniqueId;
    }

    /**
     * @param uniqueId the uniqueId to set
     */
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return the connected
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * @param connected the connected to set
     */
    public void setConnected(boolean connected) {
        this.connected = connected;
    }


    

}
