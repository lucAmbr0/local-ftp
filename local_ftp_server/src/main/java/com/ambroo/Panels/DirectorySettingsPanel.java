package com.ambroo.Panels;

import javax.swing.*;

import com.ambroo.Fonts;

import java.awt.*;

public class DirectorySettingsPanel extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(217, 217, 217);
    private static final int PANEL_WIDTH = 220;
    private static final int PANEL_HEIGHT = 110;

    private JLabel sharedFilesDirectoryLabel = new JLabel("Shared files directory");
    private JLabel pathToDirectoryLabel = new JLabel("Path to shared files folder");
    private JTextField pathField = new JTextField();
    private JButton openFolderBtn = new JButton("Open folder");
    private JButton selectFolderBtn = new JButton("Select folder");

    private String directoryPath = "D:/Mark/Documents/SharedFiles";

    public DirectorySettingsPanel() {
        setLayout(null);
        setBackground(BACKGROUND_COLOR);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        sharedFilesDirectoryLabel.setFont(Fonts.SUBTITLE_FONT);
        sharedFilesDirectoryLabel.setBounds(0, 0, 220, 26);
        pathToDirectoryLabel.setFont(Fonts.REGULAR_FONT);
        pathToDirectoryLabel.setBounds(0, 26, 220, 26);
        pathField.setFont(Fonts.REGULAR_FONT);
        pathField.setBounds(0, 52, 220, 26);
        pathField.setEditable(false);
        openFolderBtn.setBounds(0, 83, 105, 30);
        selectFolderBtn.setBounds(115, 83, 105, 30);

        pathField.setText(directoryPath);

        add(sharedFilesDirectoryLabel);
        add(pathToDirectoryLabel);
        add(pathField);
        add(openFolderBtn);
        add(selectFolderBtn);
    }

    public void setDirectoryPath(String path) {
        this.directoryPath = path;
        pathField.setText(path);
    }
    public String getDirectoryPath() { return directoryPath; }

    public JButton getOpenFolderButton() { return openFolderBtn; }
    public JButton getSelectFolderButton() { return selectFolderBtn; }
    public JTextField getPathField() { return pathField; }
}
