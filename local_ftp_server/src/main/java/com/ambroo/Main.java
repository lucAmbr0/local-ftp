package com.ambroo;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.ambroo.Windows.MainWindow;
import com.formdev.flatlaf.FlatLightLaf;

public class Main {
    public static void main(String[] args) {
        setTheme();
        new MainWindow();
    }

    private static void setTheme() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}