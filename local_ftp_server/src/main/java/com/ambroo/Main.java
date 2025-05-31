package com.ambroo;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ambroo.Windows.MainWindow;
import com.formdev.flatlaf.FlatLightLaf;
// import com.formdev.flatlaf.FlatDarkLaf;

public class Main {

    public static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Main class launched");
        setTheme();
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainWindow();
        });
    }

    private static void setTheme() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            logger.info("Theme applied");
        } catch (UnsupportedLookAndFeelException e) {
            logger.error("Failed applying theme");
            e.printStackTrace();
        }
    }
}