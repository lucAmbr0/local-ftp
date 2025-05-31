package com.ambroo.Panels;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.ambroo.Fonts;
import com.ambroo.UILogAppender;

public class LogPanel extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(217, 217, 217);

    private JLabel serverLogLabel = new JLabel("Server log");

    JTextArea logArea = new JTextArea();
    JScrollPane logScrollPane = new JScrollPane(logArea);

    public LogPanel() {
        setLayout(null);
        setBackground(BACKGROUND_COLOR);
        serverLogLabel.setFont(Fonts.SUBTITLE_FONT);
        serverLogLabel.setBounds(0, 0, 250, 26);
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        logScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(serverLogLabel);
        add(logScrollPane);
        UILogAppender.setLogListener(message -> {
            SwingUtilities.invokeLater(() -> {
                logArea.append(message + "\n");
                logArea.setCaretPosition(logArea.getDocument().getLength());
            });
        });
    }

    // Called by MainWindow to update the logArea bounds responsively
    public void updateInnerBounds(int width, int height) {
        logScrollPane.setBounds(0, 31, width - 10, height - 41);
    }

}