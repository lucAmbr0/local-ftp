package com.ambroo.Windows;

import com.ambroo.Main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.net.URI;

import com.ambroo.Panels.ServerStatusPanel;
import com.ambroo.Panels.ConnectedDevicesPanel;
import com.ambroo.Panels.DirectorySettingsPanel;
import com.ambroo.Panels.LoginPreferencesPanel;
import com.ambroo.Panels.FilesListPanel;
import com.ambroo.Panels.LogPanel;

public class MainWindow extends JFrame {
    private static final int WINDOW_WIDTH = 1100;
    private static final int WINDOW_HEIGHT = 610;
    private static final int PADDING = 20;
    private static final Color BACKGROUND_COLOR = new Color(217, 217, 217);

    JMenuBar menuBar = new JMenuBar();
    JMenu helpMenu = new JMenu("Links");
    JMenuItem appFolderLink = new JMenuItem("Open app folder", new ImageIcon(MainWindow.class.getResource("/more-icons/folder.png")));
    JMenuItem clientLink = new JMenuItem("Open server client", new ImageIcon(MainWindow.class.getResource("/more-icons/internet.png")));
    JMenuItem githubRepoLink = new JMenuItem("GitHub Repository", new ImageIcon(MainWindow.class.getResource("/more-icons/github.png")));
    JMenuItem githubProfileLink = new JMenuItem("GitHub Profile", new ImageIcon(MainWindow.class.getResource("/more-icons/github.png")));
    JMenuItem donationLink = new JMenuItem("Donate", new ImageIcon(MainWindow.class.getResource("/more-icons/kofi.png")));

    private JPanel windowContainer = new JPanel(null);
    private ServerStatusPanel serverStatusPanel;
    private DirectorySettingsPanel directorySettingsPanel;
    private LoginPreferencesPanel passwordProtectionPanel;
    private JPanel verticalSeparator = new JPanel(null);
    private FilesListPanel filesListPanel;
    private LogPanel logPanel;
    private ConnectedDevicesPanel connectedDevicesPanel;

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

        appFolderLink.addActionListener(e -> openAppFolder());
        clientLink.addActionListener(e -> openWebpage("https://github.com/lucAmbr0/local-ftp"));
        githubProfileLink.addActionListener(e -> openWebpage("https://github.com/lucAmbr0"));
        githubRepoLink.addActionListener(e -> openWebpage("https://github.com/lucAmbr0/local-ftp"));
        donationLink.addActionListener(e -> openWebpage("https://ko-fi.com/lucAmbr0"));

        helpMenu.add(appFolderLink);
        helpMenu.add(clientLink);
        helpMenu.add(githubProfileLink);
        helpMenu.add(githubRepoLink);
        helpMenu.add(donationLink);
        // Add menu to the bar
        menuBar.add(helpMenu);
        // Attach the menu bar to your frame
        setJMenuBar(menuBar);

        // Initialize panels
        serverStatusPanel = new ServerStatusPanel();
        directorySettingsPanel = new DirectorySettingsPanel();
        passwordProtectionPanel = new LoginPreferencesPanel();
        filesListPanel = new FilesListPanel();
        logPanel = new LogPanel();
        connectedDevicesPanel = new ConnectedDevicesPanel();

        // Set bounds for each panel
        serverStatusPanel.setBounds(PADDING, PADDING, 220, 210);
        directorySettingsPanel.setBounds(PADDING, PADDING + 210 + 20, 220, 120);
        passwordProtectionPanel.setBounds(PADDING, PADDING + 210 + 20 + 110 + 20, 220, 170);
        verticalSeparator.setBounds(2 * PADDING + 220, PADDING, 1, WINDOW_HEIGHT - 2 * PADDING);

        // Add panels
        windowContainer.add(serverStatusPanel);
        windowContainer.add(directorySettingsPanel);
        windowContainer.add(passwordProtectionPanel);
        windowContainer.add(verticalSeparator);
        windowContainer.add(filesListPanel);
        windowContainer.add(logPanel);
        windowContainer.add(connectedDevicesPanel);
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

    private void openWebpage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openAppFolder() {
        try {
            String currentDir = System.getProperty("user.dir");
            Desktop.getDesktop().open(new File(currentDir));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Unable to open folder.");
        }
    }

    private void updateRightPanelsLayout() {
        int width = this.getContentPane().getWidth();
        int height = this.getContentPane().getHeight();
        int rightX = 3 * PADDING + 220;
        int rightWidth = width - rightX - PADDING - 300 - PADDING; // 300 for ConnectedDevicesPanel, plus padding
        int rightHeight = height - 2 * PADDING;
        int filesListHeight = (int) (rightHeight * 2.0 / 3.0) - 5;
        int logPanelHeight = rightHeight - filesListHeight - 10;
        filesListPanel.setBounds(rightX, PADDING, rightWidth, filesListHeight);
        logPanel.setBounds(rightX, PADDING + filesListHeight + 10, rightWidth, logPanelHeight);
        filesListPanel.updateInnerBounds(rightWidth, filesListHeight);
        logPanel.updateInnerBounds(rightWidth, logPanelHeight);
        connectedDevicesPanel.setBounds(width - PADDING - 300, PADDING, 300, rightHeight);
        verticalSeparator.setBounds(2 * PADDING + 220, PADDING, 1, height - 2 * PADDING);
    }

    public ServerStatusPanel getServerStatusPanel() {
        return serverStatusPanel;
    }

    public DirectorySettingsPanel getDirectorySettingsPanel() {
        return directorySettingsPanel;
    }

    public LoginPreferencesPanel getPasswordProtectionPanel() {
        return passwordProtectionPanel;
    }
}