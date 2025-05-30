package com.ambroo.Panels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.ambroo.Fonts;
import com.ambroo.Server.Server;

public class PasswordProtectionPanel extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(217, 217, 217);
    private static final int PANEL_WIDTH = 220;
    private static final int PANEL_HEIGHT = 170;

    private JLabel passwordProtectionLabel = new JLabel("Password protection");
    private JCheckBox passwordToDownloadCheckbox = new JCheckBox("Required to download files", false);
    private JCheckBox passwordToUploadCheckbox = new JCheckBox("Required to upload files", false);
    private JLabel passwordLabel = new JLabel("Password");
    private JPasswordField passwordTextField = new JPasswordField();
    private JButton saveSettingsBtn = new JButton("Save");

    public PasswordProtectionPanel() {
        setLayout(null);
        setBackground(BACKGROUND_COLOR);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        passwordToDownloadCheckbox.setSelected(Server.isPswdToDownload());
        passwordToUploadCheckbox.setSelected(Server.isPswdToUpload());
        passwordTextField.setText(Server.getPassword());

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

        saveSettingsBtn.addActionListener(e -> {
            boolean downloadChecked = passwordToDownloadCheckbox.isSelected();
            boolean uploadChecked = passwordToUploadCheckbox.isSelected();
            String pwd = passwordTextField.getText();
            Server.setPswdToDownload(downloadChecked);
            Server.setPswdToUpload(uploadChecked);
            Server.setPassword(pwd);
        });
    }

    public void setRequirePasswordToDownload(boolean require) {
        passwordToDownloadCheckbox.setSelected(require);
    }

    public boolean isRequirePasswordToDownload() {
        return passwordToDownloadCheckbox.isSelected();
    }

    public void setRequirePasswordToUpload(boolean require) {
        passwordToUploadCheckbox.setSelected(require);
    }

    public boolean isRequirePasswordToUpload() {
        return passwordToUploadCheckbox.isSelected();
    }

    public void setPassword(String password) {
        passwordTextField.setText(password);
    }

    public String getPassword() {
        return new String(passwordTextField.getPassword());
    }

    public JButton getSaveSettingsButton() {
        return saveSettingsBtn;
    }

    public JCheckBox getPasswordToDownloadCheckbox() {
        return passwordToDownloadCheckbox;
    }

    public JCheckBox getPasswordToUploadCheckbox() {
        return passwordToUploadCheckbox;
    }

    public JTextField getPasswordTextField() {
        return passwordTextField;
    }
}
