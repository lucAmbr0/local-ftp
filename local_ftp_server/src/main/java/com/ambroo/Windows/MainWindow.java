package com.ambroo.Windows;

import javax.swing.JButton;
import javax.swing.JCheckBox;
// import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.Font;
// import javax.swing.SwingConstants;

public class MainWindow extends JFrame {

    JLabel serverSettingsLabel = new JLabel("Server Settings");
    JLabel serverStatusLabel = new JLabel("Status: Offline", SwingConstants.CENTER);
    JLabel serverInfoLabel[] = {
            new JLabel("Server IP: 0.0.0.0"),
            new JLabel("Server Port: 0"),
            new JLabel("Uptime: 00:00:00")
    };
    JButton startServerBtn = new JButton("Start");
    JButton stopServerBtn = new JButton("Stop");
    
    JLabel sharedFilesDirectoryLabel = new JLabel("Shared files directory");
    JLabel pathToDirectoryLabel = new JLabel("Path to shared files folder");

    JButton openFolderBtn = new JButton("Open folder");
    JButton selectFolderBtn = new JButton("Select folder");

    JTextField pathField = new JTextField("D:/Mark/Documents/SharedFiles");

    JLabel passwordProtectionLabel = new JLabel("Password protection");
    JCheckBox passwordToDownloadCheckbox = new JCheckBox("Required to download files", false);
    JCheckBox passwordToUploadCheckbox = new JCheckBox("Required to upload files", false);
    JLabel passwordLabel = new JLabel("Password");
    JTextField passwordTextField = new JTextField();

    JButton saveSettingsBtn = new JButton("Save");

    JPanel verticalSeparator = new JPanel();

    Font titleFont = new Font("Arial", Font.BOLD, 16);
    Font bigTextFont = new Font("Arial", Font.PLAIN, 18);
    Font serverInfoLabelFont = new Font("Arial", Font.PLAIN, 12);
    Font semiBoldTitleFont = new Font("Arial", Font.BOLD, 14);
    Font regularFont = new Font("Arial", Font.PLAIN, 12);

    JPanel windowContainer = new JPanel();
    JPanel settingsColumnPanel = new JPanel();
    JPanel serverStatePanel = new JPanel();

    public MainWindow() {
        this.setTitle("Local FTP Server");
        this.setSize(800, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        windowContainer.setLayout(null);
        settingsColumnPanel.setLayout(null);
        serverStatePanel.setLayout(null);
        windowContainer.setBounds(0, 0, 800, 600);
        settingsColumnPanel.setBounds(20, 20, 500, 600);
        serverStatePanel.setBounds(0, 31, 220, 93);
        stopServerBtn.setEnabled(false);
        pathField.setEditable(false);

        serverSettingsLabel.setBounds(0, 0, 200, 26);
        serverStatusLabel.setBounds(0, 5, 220, 24);
        for (int i = 0; i < serverInfoLabel.length; i++) {
            serverInfoLabel[i].setBounds(10, 34 + (i * 17), 220, 18);
            serverInfoLabel[i].setFont(serverInfoLabelFont);
            serverStatePanel.add(serverInfoLabel[i]);
        }
        startServerBtn.setBounds(0, 133, 105, 30);
        stopServerBtn.setBounds(115, 133, 105, 30);
        sharedFilesDirectoryLabel.setBounds(0, 223, 220, 26);
        pathToDirectoryLabel.setBounds(0, 249, 220, 26);
        pathField.setBounds(0, 275, 220, 26);
        openFolderBtn.setBounds(0, 306, 105, 30);
        selectFolderBtn.setBounds(115, 306, 105, 30);
        passwordProtectionLabel.setBounds(0, 356, 220, 26);
        passwordToDownloadCheckbox.setBounds(0, 382, 220, 26); 
        passwordToUploadCheckbox.setBounds(0, 408, 220, 26);
        passwordLabel.setBounds(0,440,220,26);
        passwordTextField.setBounds(0, 466, 220, 26);
        saveSettingsBtn.setBounds(140, 500, 80, 30);
        verticalSeparator.setBounds(240, 0, 1, 530);
        
        serverSettingsLabel.setFont(titleFont);
        serverStatusLabel.setFont(bigTextFont);
        sharedFilesDirectoryLabel.setFont(semiBoldTitleFont);
        pathToDirectoryLabel.setFont(regularFont);
        passwordProtectionLabel.setFont(semiBoldTitleFont);
        passwordToDownloadCheckbox.setFont(regularFont);
        passwordToUploadCheckbox.setFont(regularFont);

        windowContainer.setBackground(new java.awt.Color(217, 217, 217));
        settingsColumnPanel.setBackground(new java.awt.Color(217, 217, 217));
        verticalSeparator.setBackground(new java.awt.Color(170, 170, 170));
        serverStatePanel.add(serverStatusLabel);
        settingsColumnPanel.add(serverStatePanel);
        settingsColumnPanel.add(startServerBtn);
        settingsColumnPanel.add(stopServerBtn);
        settingsColumnPanel.add(sharedFilesDirectoryLabel);
        settingsColumnPanel.add(pathToDirectoryLabel);
        settingsColumnPanel.add(pathField);
        settingsColumnPanel.add(openFolderBtn);
        settingsColumnPanel.add(selectFolderBtn);
        settingsColumnPanel.add(serverSettingsLabel);
        settingsColumnPanel.add(passwordProtectionLabel);
        settingsColumnPanel.add(passwordToDownloadCheckbox);
        settingsColumnPanel.add(passwordToUploadCheckbox);
        settingsColumnPanel.add(passwordLabel);
        settingsColumnPanel.add(passwordTextField);
        settingsColumnPanel.add(saveSettingsBtn);
        settingsColumnPanel.add(verticalSeparator);
        windowContainer.add(settingsColumnPanel);
        this.add(windowContainer);
        this.add(windowContainer);
        this.setVisible(true);
    }

}