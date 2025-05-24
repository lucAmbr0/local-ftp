package com.ambroo.Panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.ambroo.Fonts;
import com.ambroo.Main;

public class ServerStatusPanel extends JPanel implements ActionListener {
    private static final Color BACKGROUND_COLOR = new Color(217, 217, 217);
    private static final Color BACKGROUND_COLOR_LIGHT = new Color(230, 230, 230);
    private static final int PANEL_WIDTH = 220;
    private static final int PANEL_HEIGHT = 180;

    private String serverIp = "0.0.0.0";
    private int serverPort = 0;
    private String uptime = "00:00:00";
    private boolean serverOnline = false;

    private JPanel serverInfoContainer = new JPanel(null);
    private JLabel serverSettingsLabel = new JLabel("Server Settings");
    private JLabel serverStatusLabel = new JLabel("Status:", SwingConstants.CENTER);
    private JLabel serverIpLabel = new JLabel("Server IP: " + serverIp);
    private JLabel serverPortLabel = new JLabel("Server Port: " + serverPort);
    private JLabel uptimeLabel = new JLabel("Uptime: " + uptime);
    private JButton startServerBtn = new JButton("Start");
    private JButton stopServerBtn = new JButton("Stop");

    public ServerStatusPanel() {
        setLayout(null);
        setBackground(BACKGROUND_COLOR);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setServerOnline(false);
        setServerIp(getLocalIpAddress());
        setServerPort(5501); // random port

        serverSettingsLabel.setFont(Fonts.TITLE_FONT);
        serverSettingsLabel.setBounds(0, 0, 200, 26);
        serverInfoContainer.setBackground(BACKGROUND_COLOR_LIGHT);
        serverInfoContainer.setBounds(0, 36, 220, 103);
        serverStatusLabel.setOpaque(true);
        serverStatusLabel.setFont(Fonts.BIG_TEXT_FONT);
        serverStatusLabel.setBounds(10, 10, 200, 28);
        serverIpLabel.setFont(Fonts.INFO_FONT);
        serverIpLabel.setBounds(10, 41, 220, 18);
        serverPortLabel.setFont(Fonts.INFO_FONT);
        serverPortLabel.setBounds(10, 59, 220, 18);
        uptimeLabel.setFont(Fonts.INFO_FONT);
        uptimeLabel.setBounds(10, 77, 220, 18);
        startServerBtn.setBounds(0, 145, 105, 30);
        startServerBtn.setName("Start");
        startServerBtn.addActionListener(this);
        stopServerBtn.setBounds(115, 145, 105, 30);
        stopServerBtn.setEnabled(false);
        stopServerBtn.setName("Stop");
        stopServerBtn.addActionListener(this);

        add(serverSettingsLabel);
        add(serverInfoContainer);
        serverInfoContainer.add(serverStatusLabel);
        serverInfoContainer.add(serverIpLabel);
        serverInfoContainer.add(serverPortLabel);
        serverInfoContainer.add(uptimeLabel);
        add(startServerBtn);
        add(stopServerBtn);
    }

    public static String getLocalIpAddress() {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if ("Start".equals(btn.getName())) {
            setServerOnline(true);
        } else if ("Stop".equals(btn.getName())) {
            setServerOnline(false);
        }
    }


    public void setServerIp(String ip) {
        this.serverIp = ip;
        serverIpLabel.setText("Server IP: " + ip);
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerPort(int port) {
        this.serverPort = port;
        serverPortLabel.setText("Server Port: " + port);
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
        uptimeLabel.setText("Uptime: " + uptime);
    }

    public String getUptime() {
        return uptime;
    }

    public void setServerOnline(boolean online) {
        this.serverOnline = online;
        serverStatusLabel.setText(online ? "Status: Online" : "Status: Offline");
        serverStatusLabel.setBackground(online ? new Color(90, 255, 95) : new Color(255, 90, 95));
        Main.logger.info(online ? "Starting server" : "Closing server");
        startServerBtn.setEnabled(!online);
        stopServerBtn.setEnabled(online);
    }

    public boolean isServerOnline() {
        return serverOnline;
    }

    public JButton getStartServerButton() {
        return startServerBtn;
    }

    public JButton getStopServerButton() {
        return stopServerBtn;
    }
}
