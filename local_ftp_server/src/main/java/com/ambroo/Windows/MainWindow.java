package com.ambroo.Windows;

// import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Font;
// import javax.swing.SwingConstants;

public class MainWindow extends JFrame {

    JLabel serverSettingsLabel = new JLabel("Server Settings");
    JLabel serverStatusLabel = new JLabel("Status: OFF", SwingConstants.CENTER);
    JLabel serverInfoLabel[] = {
        new JLabel("Server IP: 0.0.0.0"),
        new JLabel("Server Port: 0"),
        new JLabel("Uptime: 00:00:00")
    };

    Font titleFont = new Font("Arial", Font.BOLD, 16);
    Font bigTextFont = new Font("Arial", Font.PLAIN, 18);
    Font serverInfoLabelFont = new Font("Arial", Font.PLAIN, 12);

    JPanel windowContainer = new JPanel();
    JPanel settingsColumnPanel = new JPanel();
    JPanel serverStatePanel = new JPanel();

    public MainWindow() {
        this.setTitle("Local FTP Server");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        windowContainer.setLayout(null);
        settingsColumnPanel.setLayout(null);
        serverStatePanel.setLayout(null);
        windowContainer.setBounds(0, 0, 800, 600);
        settingsColumnPanel.setBounds(20, 20, 500, 600);
        serverStatePanel.setBounds(0, 31, 220, 93);

        serverSettingsLabel.setBounds(0, 0, 200, 26);
        serverStatusLabel.setBounds(0, 5, 220, 24);
        for (int i = 0; i < serverInfoLabel.length; i++) {
            serverInfoLabel[i].setBounds(10, 34 + (i * 17), 220, 18);
            serverInfoLabel[i].setFont(serverInfoLabelFont);
            serverStatePanel.add(serverInfoLabel[i]);
        }
        serverSettingsLabel.setFont(titleFont);
        serverStatusLabel.setFont(bigTextFont);

        windowContainer.setBackground(new java.awt.Color(217, 217, 217));
        settingsColumnPanel.setBackground(new java.awt.Color(217, 217, 217));
        serverStatePanel.add(serverStatusLabel);
        settingsColumnPanel.add(serverStatePanel);
        settingsColumnPanel.add(serverSettingsLabel);
        windowContainer.add(settingsColumnPanel);
        this.add(windowContainer);
        this.add(windowContainer);
        serverInfoLabel[0] = new JLabel("Server IP: 0.0.0.0");
        this.setVisible(true);
    }
}