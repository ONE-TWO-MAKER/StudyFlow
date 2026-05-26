package org.example.ui.panel;

import javax.swing.*;
import java.awt.*;

/**
 * 统计面板，展示学习打卡数据的统计图表和分析信息。
 */
public class StatisticsPanel extends JPanel {

    public StatisticsPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("数据统计", JLabel.CENTER);
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 20));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 统计卡片区
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        statsPanel.setBackground(Color.WHITE);

        statsPanel.add(createStatCard("本周打卡", "0 天"));
        statsPanel.add(createStatCard("本月打卡", "0 天"));
        statsPanel.add(createStatCard("学习任务", "0 个"));

        add(statsPanel, BorderLayout.NORTH);

        // 图表占位区
        JPanel chartPanel = new JPanel(new GridBagLayout());
        chartPanel.setBackground(new Color(250, 250, 250));
        chartPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        JLabel chartLabel = new JLabel("打卡趋势图（待实现）", JLabel.CENTER);
        chartLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        chartLabel.setForeground(new Color(189, 195, 199));
        chartPanel.add(chartLabel);

        add(chartPanel, BorderLayout.CENTER);
    }

    private JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(245, 247, 250));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 223, 230), 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        titleLabel.setForeground(new Color(127, 140, 141));

        JLabel valueLabel = new JLabel(value, JLabel.CENTER);
        valueLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 24));
        valueLabel.setForeground(new Color(44, 62, 80));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }
}
