package com.ambroo.Windows;

import com.ambroo.Main;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import com.ambroo.Panels.ServerStatusPanel;
import com.ambroo.Panels.DirectorySettingsPanel;
import com.ambroo.Panels.PasswordProtectionPanel;
import com.ambroo.Panels.FilesListPanel;
import com.ambroo.Panels.LogPanel;

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
    private FilesListPanel filesListPanel;
    private LogPanel logPanel;

    public MainWindow() {
        this.setTitle("Local FTP Server");
        this.setMinimumSize(new java.awt.Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setIconImage(
                Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/icons/local-ftp-server-icon.png")));
        windowContainer.setBackground(BACKGROUND_COLOR);
        windowContainer.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        verticalSeparator.setBackground(new Color(100, 100, 100));
        verticalSeparator.setBounds(2 * PADDING + 220, PADDING, 1, WINDOW_HEIGHT - 2 * PADDING);

        // Initialize panels
        serverStatusPanel = new ServerStatusPanel();
        directorySettingsPanel = new DirectorySettingsPanel();
        passwordProtectionPanel = new PasswordProtectionPanel();
        filesListPanel = new FilesListPanel();
        logPanel = new LogPanel();

        // Set bounds for each panel
        serverStatusPanel.setBounds(PADDING, PADDING, 220, 180);
        directorySettingsPanel.setBounds(PADDING, PADDING + 180 + 20, 220, 120);
        passwordProtectionPanel.setBounds(PADDING, PADDING + 180 + 20 + 110 + 20, 220, 170);
        verticalSeparator.setBounds(2 * PADDING + 220, PADDING, 1, WINDOW_HEIGHT - 2 * PADDING);

        // Add panels
        windowContainer.add(serverStatusPanel);
        windowContainer.add(directorySettingsPanel);
        windowContainer.add(passwordProtectionPanel);
        windowContainer.add(verticalSeparator);
        windowContainer.add(filesListPanel);
        windowContainer.add(logPanel);
        this.add(windowContainer);
        this.setVisible(true);
        Main.logger.info("UI loaded");

        // Responsive layout for right panels
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateRightPanelsLayout();
            }
        });
        updateRightPanelsLayout();
    }

    private void updateRightPanelsLayout() {
        int width = this.getContentPane().getWidth();
        int height = this.getContentPane().getHeight();
        int rightX = 3 * PADDING + 220;
        int rightWidth = width - rightX - PADDING;
        int rightHeight = height - 2 * PADDING;
        int filesListHeight = (int) (rightHeight * 2.0 / 3.0) - 5;
        int logPanelHeight = rightHeight - filesListHeight - 10;
        filesListPanel.setBounds(rightX, PADDING, rightWidth, filesListHeight);
        logPanel.setBounds(rightX, PADDING + filesListHeight + 10, rightWidth, logPanelHeight);
        filesListPanel.updateInnerBounds(rightWidth, filesListHeight);
        logPanel.updateInnerBounds(rightWidth, logPanelHeight);
        verticalSeparator.setBounds(2 * PADDING + 220, PADDING, 1, height - 2 * PADDING);
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