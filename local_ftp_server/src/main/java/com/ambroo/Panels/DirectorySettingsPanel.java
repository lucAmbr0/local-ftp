package com.ambroo.Panels;

import javax.swing.*;
import com.ambroo.Fonts;
import com.ambroo.Main;
import com.ambroo.Server.Server;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class DirectorySettingsPanel extends JPanel implements ActionListener {
    private static final Color BACKGROUND_COLOR = new Color(217, 217, 217);
    private static final int PANEL_WIDTH = 220;
    private static final int PANEL_HEIGHT = 120;

    private JLabel sharedFilesDirectoryLabel = new JLabel("Shared files directory");
    private JLabel pathToDirectoryLabel = new JLabel("Path to shared files folder");
    private JTextField pathField = new JTextField();
    private JButton openFolderBtn = new JButton("Open folder");
    private JButton selectFolderBtn = new JButton("Select folder");
    private JFileChooser folderChooser;

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
        openFolderBtn.setName("Open folder");
        openFolderBtn.addActionListener(this);
        selectFolderBtn.setBounds(115, 83, 105, 30);
        selectFolderBtn.setName("Select folder");
        selectFolderBtn.addActionListener(this);

        pathField.setText(Server.getServerPath());

        add(sharedFilesDirectoryLabel);
        add(pathToDirectoryLabel);
        add(pathField);
        add(openFolderBtn);
        add(selectFolderBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if ("Open folder".equals(btn.getName())) {
            try {
                File folder = new File(Server.getServerPath());
                if (Desktop.isDesktopSupported() && folder.exists() && folder.isDirectory()) {
                    Desktop.getDesktop().open(folder);
                    Main.logger.error("Opened folder: " + folder.getAbsolutePath());
                } else {
                    Main.logger.error(
                            "Could not open folder. Either Desktop is not supported, or the folder does not exist/is not a directory: "
                                    + Server.getServerPath());
                }
            } catch (IOException a) {
                Main.logger.error("An error occurred while trying to open the folder: " + a.getMessage());
                a.printStackTrace();
            }
            return;
        } else if ("Select folder".equals(btn.getName())) {
            folderChooser = new JFileChooser();
            folderChooser.setCurrentDirectory(new java.io.File("."));
            folderChooser.setDialogTitle("Select server directory");
            folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            folderChooser.setAcceptAllFileFilterUsed(false);
            if (folderChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                String selectedPath = folderChooser.getSelectedFile().toString();
                Main.logger.info("Changed server directory to " + selectedPath);
                setDirectoryPath(selectedPath);
                Server.setServerPath(selectedPath);
            } else {
                Main.logger.info("No Selection ");
            }
            return;
        }
    }

    public void setDirectoryPath(String path) {
        pathField.setText(path);
    }

    public String getDirectoryPath() {
        return Server.getServerPath();
    }

    public JButton getOpenFolderButton() {
        return openFolderBtn;
    }

    public JButton getSelectFolderButton() {
        return selectFolderBtn;
    }

    public JTextField getPathField() {
        return pathField;
    }
}
