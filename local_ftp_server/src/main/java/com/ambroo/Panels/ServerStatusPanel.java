package com.ambroo.Panels;

import javax.swing.*;

import com.ambroo.Fonts;

import java.awt.*;

public class ServerStatusPanel extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(217, 217, 217);
    private static final Color BACKGROUND_COLOR_LIGHT = new Color(230, 230, 230);
    private static final int PANEL_WIDTH = 220;
    private static final int PANEL_HEIGHT = 180;

    private JPanel serverInfoContainer = new JPanel(null);
    private JLabel serverSettingsLabel = new JLabel("Server Settings");
    private JLabel serverStatusLabel = new JLabel("Status: Offline", SwingConstants.CENTER);
    private JLabel serverIpLabel = new JLabel("Server IP: 0.0.0.0");
    private JLabel serverPortLabel = new JLabel("Server Port: 0");
    private JLabel uptimeLabel = new JLabel("Uptime: 00:00:00");
    private JButton startServerBtn = new JButton("Start");
    private JButton stopServerBtn = new JButton("Stop");

    private String serverIp = "0.0.0.0";
    private int serverPort = 0;
    private String uptime = "00:00:00";
    private boolean serverOnline = false;

    public ServerStatusPanel() {
        setLayout(null);
        setBackground(BACKGROUND_COLOR);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        serverSettingsLabel.setFont(Fonts.TITLE_FONT);
        serverSettingsLabel.setBounds(0, 0, 200, 26);
        serverInfoContainer.setBackground(BACKGROUND_COLOR_LIGHT);
        serverInfoContainer.setBounds(0, 36, 220, 103);
        serverStatusLabel.setFont(Fonts.BIG_TEXT_FONT);
        serverStatusLabel.setBounds(0, 10, 220, 24);
        serverIpLabel.setFont(Fonts.INFO_FONT);
        serverIpLabel.setBounds(10, 39, 220, 18);
        serverPortLabel.setFont(Fonts.INFO_FONT);
        serverPortLabel.setBounds(10, 57, 220, 18);
        uptimeLabel.setFont(Fonts.INFO_FONT);
        uptimeLabel.setBounds(10, 75, 220, 18);
        startServerBtn.setBounds(0, 143, 105, 30);
        stopServerBtn.setBounds(115, 143, 105, 30);
        stopServerBtn.setEnabled(false);

        add(serverSettingsLabel);
        add(serverInfoContainer);
        serverInfoContainer.add(serverStatusLabel);
        serverInfoContainer.add(serverIpLabel);
        serverInfoContainer.add(serverPortLabel);
        serverInfoContainer.add(uptimeLabel);
        add(startServerBtn);
        add(stopServerBtn);
    }

    public void setServerIp(String ip) {
        this.serverIp = ip;
        serverIpLabel.setText("Server IP: " + ip);
    }
    public String getServerIp() { return serverIp; }

    public void setServerPort(int port) {
        this.serverPort = port;
        serverPortLabel.setText("Server Port: " + port);
    }
    public int getServerPort() { return serverPort; }

    public void setUptime(String uptime) {
        this.uptime = uptime;
        uptimeLabel.setText("Uptime: " + uptime);
    }
    public String getUptime() { return uptime; }

    public void setServerOnline(boolean online) {
        this.serverOnline = online;
        serverStatusLabel.setText(online ? "Status: Online" : "Status: Offline");
        startServerBtn.setEnabled(!online);
        stopServerBtn.setEnabled(online);
    }
    public boolean isServerOnline() { return serverOnline; }

    public JButton getStartServerButton() { return startServerBtn; }
    public JButton getStopServerButton() { return stopServerBtn; }
}
