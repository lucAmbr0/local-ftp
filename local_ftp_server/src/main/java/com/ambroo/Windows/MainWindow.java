package com.ambroo.Windows;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import com.ambroo.Panels.ServerStatusPanel;
import com.ambroo.Panels.DirectorySettingsPanel;
import com.ambroo.Panels.PasswordProtectionPanel;

public class MainWindow extends JFrame {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 580;
    private static final int PADDING = 20;
    private static final Color BACKGROUND_COLOR = new Color(217, 217, 217);

    private JPanel windowContainer = new JPanel(null);
    private ServerStatusPanel serverStatusPanel;
    private DirectorySettingsPanel directorySettingsPanel;
    private PasswordProtectionPanel passwordProtectionPanel;
    private JPanel verticalSeparator = new JPanel(null);

    public MainWindow() {
        this.setTitle("Local FTP Server");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        windowContainer.setBackground(BACKGROUND_COLOR);
        windowContainer.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        verticalSeparator.setBackground(new Color(100, 100, 100));
        verticalSeparator.setBounds(2 * PADDING + 220, PADDING, 1, 180 + 110 + 170 + PADDING * 2);

        // Initialize panels
        serverStatusPanel = new ServerStatusPanel();
        directorySettingsPanel = new DirectorySettingsPanel();
        passwordProtectionPanel = new PasswordProtectionPanel();

        // Set bounds for each panel
        serverStatusPanel.setBounds(PADDING, PADDING, 220, 180);
        directorySettingsPanel.setBounds(PADDING, PADDING + 180 + 20, 220, 110);
        passwordProtectionPanel.setBounds(PADDING, PADDING + 180 + 20 + 110 + 20, 220, 170);

        windowContainer.add(serverStatusPanel);
        windowContainer.add(directorySettingsPanel);
        windowContainer.add(passwordProtectionPanel);
        windowContainer.add(verticalSeparator);
        this.add(windowContainer);
        this.setVisible(true);
    }

    public ServerStatusPanel getServerStatusPanel() {
        return serverStatusPanel;
    }

    public DirectorySettingsPanel getDirectorySettingsPanel() {
        return directorySettingsPanel;
    }

    public PasswordProtectionPanel getPasswordProtectionPanel() {
        return passwordProtectionPanel;
    }
}