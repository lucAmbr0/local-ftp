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

public class LoginPreferencesPanel extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(217, 217, 217);
    private static final int PANEL_WIDTH = 220;
    private static final int PANEL_HEIGHT = 170;

    private JLabel passwordProtectionLabel = new JLabel("Password protection");
    private JCheckBox requireNickname = new JCheckBox("Require nickname", false);
    private JCheckBox requirePassword = new JCheckBox("Require password", false);
    private JLabel passwordLabel = new JLabel("Password");
    private JPasswordField passwordTextField = new JPasswordField();
    private JButton saveSettingsBtn = new JButton("Save");

    public LoginPreferencesPanel() {
        setLayout(null);
        setBackground(BACKGROUND_COLOR);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        requireNickname.setSelected(Server.isRequireNickname());
        requirePassword.setSelected(Server.isRequirePassword());
        passwordTextField.setEnabled(Server.isRequirePassword());
        passwordTextField.setText(Server.getPassword());

        passwordProtectionLabel.setFont(Fonts.SUBTITLE_FONT);
        passwordProtectionLabel.setBounds(0, 0, 220, 26);
        requireNickname.setFont(Fonts.REGULAR_FONT);
        requireNickname.setBounds(0, 26, 220, 26);
        requirePassword.setFont(Fonts.REGULAR_FONT);
        requirePassword.setBounds(0, 52, 220, 26);
        passwordLabel.setFont(Fonts.REGULAR_FONT);
        passwordLabel.setBounds(0, 78, 220, 26);
        passwordTextField.setBounds(0, 104, 220, 26);
        saveSettingsBtn.setBounds(140, 140, 80, 30);

        add(passwordProtectionLabel);
        add(requireNickname);
        add(requirePassword);
        add(passwordLabel);
        add(passwordTextField);
        add(saveSettingsBtn);

        requireNickname.addActionListener(e -> {
            boolean requireNicknameChecked = requireNickname.isSelected();
            Server.setRequireNickname(requireNicknameChecked);
        });
        
        requirePassword.addActionListener(e -> {
            boolean requirePasswordChecked = requirePassword.isSelected();
            passwordTextField.setEnabled(requirePasswordChecked);
            Server.setRequirePassword(requirePasswordChecked);
        });

        saveSettingsBtn.addActionListener(e -> {
            String pwd = passwordTextField.getText();
            Server.setPassword(pwd);
        });
    }

    public void setRequirePasswordToDownload(boolean require) {
        requireNickname.setSelected(require);
    }

    public boolean isRequirePasswordToDownload() {
        return requireNickname.isSelected();
    }

    public void setRequirePasswordToUpload(boolean require) {
        requirePassword.setSelected(require);
    }

    public boolean isRequirePasswordToUpload() {
        return requirePassword.isSelected();
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

    public JCheckBox getRequireNickname() {
        return requireNickname;
    }

    public JCheckBox getRequirePassword() {
        return requirePassword;
    }

    public JTextField getPasswordTextField() {
        return passwordTextField;
    }
}
