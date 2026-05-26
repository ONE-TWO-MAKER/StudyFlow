package org.example.ui.panel;

import javax.swing.*;
import java.awt.*;

/**
 * 打卡面板，提供每日学习打卡操作界面。
 */
public class CheckInPanel extends JPanel {

    public CheckInPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("学习打卡", JLabel.CENTER);
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 20));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 打卡按钮区域
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);

        JButton checkInBtn = new JButton("今日打卡");
        checkInBtn.setFont(new Font("Microsoft YaHei", Font.BOLD, 20));
        checkInBtn.setBackground(new Color(26, 188, 156));
        checkInBtn.setForeground(Color.WHITE);
        checkInBtn.setBorderPainted(false);
        checkInBtn.setFocusPainted(false);
        checkInBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkInBtn.setPreferredSize(new Dimension(200, 200));
        checkInBtn.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        centerPanel.add(checkInBtn);
        add(centerPanel, BorderLayout.CENTER);
    }
}
