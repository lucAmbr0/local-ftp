package com.ambroo.Panels;

import javax.swing.*;

import com.ambroo.Fonts;

import java.awt.*;

public class PasswordProtectionPanel extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(217, 217, 217);
    private static final int PANEL_WIDTH = 220;
    private static final int PANEL_HEIGHT = 170;

    private JLabel passwordProtectionLabel = new JLabel("Password protection");
    private JCheckBox passwordToDownloadCheckbox = new JCheckBox("Required to download files", false);
    private JCheckBox passwordToUploadCheckbox = new JCheckBox("Required to upload files", false);
    private JLabel passwordLabel = new JLabel("Password");
    private JTextField passwordTextField = new JTextField();
    private JButton saveSettingsBtn = new JButton("Save");

    private boolean requirePasswordToDownload = false;
    private boolean requirePasswordToUpload = false;
    private String password = "";

    public PasswordProtectionPanel() {
        setLayout(null);
        setBackground(BACKGROUND_COLOR);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        passwordProtectionLabel.setFont(Fonts.SUBTITLE_FONT);
        passwordProtectionLabel.setBounds(0, 0, 220, 26);
        passwordToDownloadCheckbox.setFont(Fonts.REGULAR_FONT);
        passwordToDownloadCheckbox.setBounds(0, 26, 220, 26);
        passwordToUploadCheckbox.setFont(Fonts.REGULAR_FONT);
        passwordToUploadCheckbox.setBounds(0, 52, 220, 26);
        passwordLabel.setFont(Fonts.REGULAR_FONT);
        passwordLabel.setBounds(0, 78, 220, 26);
        passwordTextField.setBounds(0, 104, 220, 26);
        saveSettingsBtn.setBounds(140, 140, 80, 30);

        add(passwordProtectionLabel);
        add(passwordToDownloadCheckbox);
        add(passwordToUploadCheckbox);
        add(passwordLabel);
        add(passwordTextField);
        add(saveSettingsBtn);
    }

    public void setRequirePasswordToDownload(boolean require) {
        this.requirePasswordToDownload = require;
        passwordToDownloadCheckbox.setSelected(require);
    }
    public boolean isRequirePasswordToDownload() { return requirePasswordToDownload; }

    public void setRequirePasswordToUpload(boolean require) {
        this.requirePasswordToUpload = require;
        passwordToUploadCheckbox.setSelected(require);
    }
    public boolean isRequirePasswordToUpload() { return requirePasswordToUpload; }

    public void setPassword(String password) {
        this.password = password;
        passwordTextField.setText(password);
    }
    public String getPassword() { return password; }

    public JButton getSaveSettingsButton() { return saveSettingsBtn; }
    public JCheckBox getPasswordToDownloadCheckbox() { return passwordToDownloadCheckbox; }
    public JCheckBox getPasswordToUploadCheckbox() { return passwordToUploadCheckbox; }
    public JTextField getPasswordTextField() { return passwordTextField; }
}
