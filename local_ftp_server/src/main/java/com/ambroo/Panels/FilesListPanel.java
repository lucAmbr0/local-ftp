package com.ambroo.Panels;

import java.awt.Color;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.ambroo.Fonts;
import com.ambroo.Server.Server;

public class FilesListPanel extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(217, 217, 217);

    private JLabel filesListLabel = new JLabel("Files list");

    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel model;

    private final String[] columnNames = { "File name", "Date added", "Downloads" };

    public FilesListPanel() {

        setLayout(null);
        setBackground(BACKGROUND_COLOR);

        filesListLabel.setFont(Fonts.SUBTITLE_FONT);
        filesListLabel.setBounds(0, 0, 250, 26);

        model = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // make all cells uneditable
            }
        };

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        scrollPane = new JScrollPane(table);

        add(filesListLabel);
        add(scrollPane);

        startAutoRefresh();
    }

    public void updateInnerBounds(int width, int height) {
        scrollPane.setBounds(0, 31, width - 10, height - 41);
        table.setPreferredScrollableViewportSize(scrollPane.getSize());
        table.setSize(scrollPane.getSize());
        table.revalidate();
        table.repaint();
    }

    private void refreshFileList() {
        File dir = new File(Server.getServerPath());
        if (!dir.exists() || !dir.isDirectory()) return;

        File[] files = dir.listFiles();
        if (files == null) return;

        model.setRowCount(0); // clear table

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (File file : files) {
            if (file.isFile()) {
                String name = file.getName();
                String date = sdf.format(new Date(file.lastModified()));
                int downloads = 0; // placeholder, depends on your app logic
                model.addRow(new Object[] { name, date, downloads });
            }
        }
    }

    private void startAutoRefresh() {
        Timer timer = new Timer(1000, e -> refreshFileList()); // every 1 sec
        timer.start();
    }
}
