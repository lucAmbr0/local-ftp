package com.ambroo.Panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import com.ambroo.Fonts;
import com.ambroo.Main;
import com.ambroo.Server.Server;

public class ServerStatusPanel extends JPanel implements ActionListener {
    private static final Color BACKGROUND_COLOR = new Color(217, 217, 217);
    private static final Color BACKGROUND_COLOR_LIGHT = new Color(230, 230, 230);
    private static final int PANEL_WIDTH = 220;
    private static final int PANEL_HEIGHT = 210;

    private JPanel serverInfoContainer = new JPanel(null);
    private JLabel serverSettingsLabel = new JLabel("Server Settings");
    public static JLabel serverStatusLabel = new JLabel("Status: Offline",SwingConstants.CENTER);
    private JLabel serverIpLabel = new JLabel("Server IP: ");
    private JLabel serverPortLabel = new JLabel("Server Port: ");
    private static JLabel uptimeLabel = new JLabel("Uptime: 00:00:00");
    public static JButton startServerBtn = new JButton("Start");
    public static JButton stopServerBtn = new JButton("Stop");
    private JCheckBox autostartCheckbox = new JCheckBox("Auto-start at launch", false);
    private static long serverStartTime = 0;
    private static javax.swing.Timer uptimeTimer;

    public ServerStatusPanel() {
        setLayout(null);
        setBackground(BACKGROUND_COLOR);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        autostartCheckbox.setSelected(Server.isAutostart());
        serverStatusLabel.setBackground(new Color(255,90,95));
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
        setServerIp(Server.getIp());
        setServerPort(Server.getPort());
        uptimeLabel.setFont(Fonts.INFO_FONT);
        uptimeLabel.setBounds(10, 77, 220, 18);
        startServerBtn.setBounds(0, 145, 105, 30);
        startServerBtn.setName("Start");
        startServerBtn.addActionListener(this);
        stopServerBtn.setBounds(115, 145, 105, 30);
        stopServerBtn.setEnabled(false);
        stopServerBtn.setName("Stop");
        stopServerBtn.addActionListener(this);
        autostartCheckbox.setBounds(0, 180, 200, 26);
        add(serverSettingsLabel);
        add(serverInfoContainer);
        serverInfoContainer.add(serverStatusLabel);
        serverInfoContainer.add(serverIpLabel);
        serverInfoContainer.add(serverPortLabel);
        serverInfoContainer.add(uptimeLabel);
        add(startServerBtn);
        add(stopServerBtn);
        add(autostartCheckbox);
        uptimeTimer = new javax.swing.Timer(1000, e -> updateUptime());

        autostartCheckbox.addActionListener(e -> {
            boolean autostartChecked = autostartCheckbox.isSelected();
            Server.setAutostart(autostartChecked);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if ("Start".equals(btn.getName())) {
           Server.start();
        } else if ("Stop".equals(btn.getName())) {
            Server.stop();
        }
    }

    public void setServerIp(String serverIp) {
        serverIpLabel.setText("Server IP: " + serverIp);
    }

    public String getServerIp() {
        return Server.getIp();
    }

    public void setServerPort(int port) {
        serverPortLabel.setText("Server Port: " + port);
    }

    public int getServerPort() {
        return Server.getPort();
    }

    public static void setServerOnline(boolean online) {
        serverStatusLabel.setText(online ? "Status: Online" : "Status: Offline");
        serverStatusLabel.setBackground(online ? new Color(90, 255, 95) : new Color(255, 90, 95));
        Main.logger.info(online ? "Server started" : "Server closed");
        startServerBtn.setEnabled(!online);
        stopServerBtn.setEnabled(online);
        if (online) {
            serverStartTime = System.currentTimeMillis();
            uptimeLabel.setText("Uptime: 00:00:00");
            uptimeTimer.start();
        } else {
            uptimeTimer.stop();
            uptimeLabel.setText("Uptime: 00:00:00");
        }
    }

    private void updateUptime() {
        long elapsed = (System.currentTimeMillis() - serverStartTime) / 1000;
        long hours = elapsed / 3600;
        long minutes = (elapsed % 3600) / 60;
        long seconds = elapsed % 60;
        String formatted = String.format("Uptime: %02d:%02d:%02d", hours, minutes, seconds);
        uptimeLabel.setText(formatted);
    }

    public boolean isServerOnline() {
        return Server.isAutostart();
    }

    public JButton getStartServerButton() {
        return startServerBtn;
    }

    public JButton getStopServerButton() {
        return stopServerBtn;
    }

    
}
