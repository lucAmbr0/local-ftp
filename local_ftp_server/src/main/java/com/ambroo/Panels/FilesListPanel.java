package com.ambroo.Panels;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.ambroo.Fonts;

public class FilesListPanel extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(217, 217, 217);

    private JLabel filesListLabel = new JLabel("Files list");

    JFrame frame = new JFrame("Files List");
    String[] columnNames = { "File name", "Date added", "Downloads" };
    Object[][] data = {
            { "Timeless - The...", "12/03/2025 13:52", 12 },
            { "Concert_Tickets", "12/03/2025 13:51", 5 },
            { "Document.pdf", "12/03/2025 13:51", 3 },
            { "Obama2502.jpg", "12/03/2025 13:51", 0 },
            { "IdkWtf.jpg", "11/03/2025 08:10", 0 },
            { "CuteCat.png", "11/03/2025 08:10", 3 },
            { "Spongebob.pdf", "11/03/2025 08:10", 1 }
    };

    DefaultTableModel model = new DefaultTableModel(data, columnNames);
    JTable table = new JTable(model);
    JScrollPane scrollPane = new JScrollPane(table);

    public FilesListPanel() {
        setLayout(null);
        setBackground(BACKGROUND_COLOR);

        filesListLabel.setFont(Fonts.SUBTITLE_FONT);
        filesListLabel.setBounds(0, 0, 250, 26);

        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        add(filesListLabel);
        add(scrollPane);
    }

    public void updateInnerBounds(int width, int height) {
        scrollPane.setBounds(0, 31, width - 10, height - 41);
        table.setPreferredScrollableViewportSize(scrollPane.getSize());
        table.setSize(scrollPane.getSize());
        table.revalidate();
        table.repaint();
    }

}
