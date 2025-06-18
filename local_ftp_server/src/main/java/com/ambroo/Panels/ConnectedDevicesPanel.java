package com.ambroo.Panels;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import com.ambroo.Fonts;

public class ConnectedDevicesPanel extends JPanel {
    private static final Color BACKGROUND_COLOR = new Color(255, 255, 255);
    private static final int ITEM_HEIGHT = 60;
    private static final int ITEM_MARGIN = 5;
    private static final int PANEL_WIDTH = 300;

    private JLabel titleLabel = new JLabel("Connected devices");
    private static JPanel devicesContainer = new JPanel(null);
    private static JScrollPane scrollPane;
    private final static List<JPanel> devicePanels = new ArrayList<>();

    public ConnectedDevicesPanel() {
        setLayout(null);
        setBackground(new Color(217, 217, 217));
        titleLabel.setFont(Fonts.SUBTITLE_FONT);
        titleLabel.setBounds(0, 0, PANEL_WIDTH - 16, 26);
        add(titleLabel);

        devicesContainer.setBackground(BACKGROUND_COLOR);
        devicesContainer.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        devicesContainer.setBounds(0, 0, PANEL_WIDTH, 1000);
        scrollPane = new JScrollPane(devicesContainer, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.setBounds(0, 31, PANEL_WIDTH, 1000);
        // Make scrolling more sensitive
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setUnitIncrement(30); // Increase scroll sensitivity
        add(scrollPane);
        revalidate();
        repaint();
    }

    public static void addDevice(String nickname, String ip) {
        JPanel devicePanel = new JPanel(null);
        devicePanel.setBackground(new Color(217, 217, 217));
        int scrollbarWidth = scrollPane != null ? scrollPane.getVerticalScrollBar().getPreferredSize().width : 16;
        int deviceWidth = PANEL_WIDTH - 20 - scrollbarWidth; // 10px margin each side, minus scrollbar
        int y = devicePanels.size() * (ITEM_HEIGHT + ITEM_MARGIN);
        devicePanel.setBounds(10, y + 10, deviceWidth, ITEM_HEIGHT); // 10px margin on all sides

        int labelHeight = 20;
        int totalLabelHeight = 20 + 4 + 20;
        int startY = (ITEM_HEIGHT - totalLabelHeight) / 2;

        JLabel nameLabel = new JLabel(nickname);
        nameLabel.setFont(Fonts.REGULAR_BOLD_FONT);
        nameLabel.setBounds(16, startY, PANEL_WIDTH - 140, labelHeight);
        devicePanel.add(nameLabel);

        JLabel ipLabel = new JLabel(ip);
        ipLabel.setFont(Fonts.REGULAR_FONT);
        ipLabel.setBounds(16, startY + labelHeight + 4, PANEL_WIDTH - 140, labelHeight);
        devicePanel.add(ipLabel);

        String[] roles = {"All", "Upload", "Download", "Viewer", "Blocked"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        roleBox.setFont(Fonts.REGULAR_FONT);
        roleBox.setBounds(devicePanel.getWidth() - 110, (ITEM_HEIGHT - 30) / 2, 100, 30);
        devicePanel.add(roleBox);

        devicePanels.add(devicePanel);
        devicesContainer.add(devicePanel);
        // Update container preferred size for scrolling
        int totalHeight = 10 + devicePanels.size() * (ITEM_HEIGHT + ITEM_MARGIN);
        devicesContainer.setPreferredSize(new java.awt.Dimension(PANEL_WIDTH, totalHeight));
        devicesContainer.revalidate();
        devicesContainer.repaint();
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, PANEL_WIDTH, height);
        titleLabel.setBounds(8, 0, PANEL_WIDTH - 16, 26);
        scrollPane.setBounds(0, 31, PANEL_WIDTH, height - 31);
        int scrollbarWidth = scrollPane.getVerticalScrollBar().getPreferredSize().width;
        int deviceWidth = PANEL_WIDTH - 20 - scrollbarWidth;
        devicesContainer.setBounds(0, 0, PANEL_WIDTH, Math.max(height - 31, devicesContainer.getPreferredSize().height));
        for (int i = 0; i < devicePanels.size(); i++) {
            int yPanel = 10 + i * (ITEM_HEIGHT + ITEM_MARGIN);
            devicePanels.get(i).setBounds(10, yPanel, deviceWidth, ITEM_HEIGHT);
            JPanel devicePanel = devicePanels.get(i);
            JComboBox<?> box = (JComboBox<?>) devicePanel.getComponent(2);
            box.setBounds(devicePanel.getWidth() - 110, (ITEM_HEIGHT - 30) / 2, 100, 30);
            JLabel nameLabel = (JLabel) devicePanel.getComponent(0);
            JLabel ipLabel = (JLabel) devicePanel.getComponent(1);
            int labelHeight = 20;
            int totalLabelHeight = 20 + 4 + 20;
            int startY = (ITEM_HEIGHT - totalLabelHeight) / 2;
            nameLabel.setBounds(16, startY, deviceWidth - 130, labelHeight);
            ipLabel.setBounds(16, startY + labelHeight + 4, deviceWidth - 130, labelHeight);
        }
        devicesContainer.setPreferredSize(new java.awt.Dimension(PANEL_WIDTH, 10 + devicePanels.size() * (ITEM_HEIGHT + ITEM_MARGIN)));
        devicesContainer.revalidate();
    }

    @Override
    public void doLayout() {
        int h = getHeight();
        if (getWidth() != PANEL_WIDTH) {
            setSize(PANEL_WIDTH, h);
        }
        titleLabel.setBounds(8, 0, PANEL_WIDTH - 16, 26);
        scrollPane.setBounds(0, 31, PANEL_WIDTH, h - 31);
        int scrollbarWidth = scrollPane.getVerticalScrollBar().getPreferredSize().width;
        int deviceWidth = PANEL_WIDTH - 20 - scrollbarWidth;
        devicesContainer.setBounds(0, 0, PANEL_WIDTH, Math.max(h - 31, devicesContainer.getPreferredSize().height));
        for (int i = 0; i < devicePanels.size(); i++) {
            int yPanel = 10 + i * (ITEM_HEIGHT + ITEM_MARGIN);
            devicePanels.get(i).setBounds(10, yPanel, deviceWidth, ITEM_HEIGHT);
            JPanel devicePanel = devicePanels.get(i);
            JComboBox<?> box = (JComboBox<?>) devicePanel.getComponent(2);
            box.setBounds(devicePanel.getWidth() - 110, (ITEM_HEIGHT - 30) / 2, 100, 30);
            JLabel nameLabel = (JLabel) devicePanel.getComponent(0);
            JLabel ipLabel = (JLabel) devicePanel.getComponent(1);
            int labelHeight = 20;
            int totalLabelHeight = 20 + 4 + 20;
            int startY = (ITEM_HEIGHT - totalLabelHeight) / 2;
            nameLabel.setBounds(16, startY, deviceWidth - 130, labelHeight);
            ipLabel.setBounds(16, startY + labelHeight + 4, deviceWidth - 130, labelHeight);
        }
        devicesContainer.setPreferredSize(new java.awt.Dimension(PANEL_WIDTH, 10 + devicePanels.size() * (ITEM_HEIGHT + ITEM_MARGIN)));
        devicesContainer.revalidate();
    }
}
