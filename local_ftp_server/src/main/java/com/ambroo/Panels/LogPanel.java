package com.ambroo.Panels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.ambroo.Fonts;
import com.ambroo.UILogAppender;

public class LogPanel extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(217, 217, 217);
    private static final int PANEL_WIDTH = 520;
    private static final int PANEL_HEIGHT = 200;

    private JLabel serverLogLabel = new JLabel("Server log");

    JTextArea logArea = new JTextArea();

    public LogPanel() {
        setLayout(null);
        setBackground(BACKGROUND_COLOR);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        serverLogLabel.setFont(Fonts.SUBTITLE_FONT);
        serverLogLabel.setBounds(0, 0, 250, 26);

        logArea.setEditable(false);
        logArea.setBounds(0, 31, PANEL_WIDTH - 30, PANEL_HEIGHT - 41);

        UILogAppender.setLogListener(message -> {
            SwingUtilities.invokeLater(() -> {
                logArea.append(message + "\n");
                logArea.setCaretPosition(logArea.getDocument().getLength());
            });
        });

        add(serverLogLabel);
        add(logArea);
    }

}
