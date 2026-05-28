package org.example.ui.panel;

import org.example.model.CheckInRecord;
import org.example.model.User;
import org.example.service.CheckInService;
import org.example.service.TaskService;
import org.example.util.DateUtil;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 统计面板，展示学习打卡数据的统计图表和分析信息。
 */
public class StatisticsPanel extends JPanel {

    private final User currentUser;
    private final TaskService taskService;
    private final CheckInService checkInService;

    private JLabel weeklyLabel;
    private JLabel monthlyLabel;
    private JLabel taskCountLabel;

    public StatisticsPanel(User currentUser, TaskService taskService, CheckInService checkInService) {
        this.currentUser = currentUser;
        this.taskService = taskService;
        this.checkInService = checkInService;

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

        switch (title) {
            case "本周打卡":
                weeklyLabel = valueLabel;
                break;
            case "本月打卡":
                monthlyLabel = valueLabel;
                break;
            case "学习任务":
                taskCountLabel = valueLabel;
                break;
        }

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    /**
     * 从数据库加载最新数据并更新统计卡片。
     * 使用 DateUtil 过滤本周/本月的打卡记录，按日期去重统计独立天数。
     */
    public void loadData() {
        List<CheckInRecord> records = checkInService.getAllRecords(currentUser.getId());
        int taskCount = taskService.getAllTasks(currentUser.getId()).size();

        Set<String> weeklyDates = new HashSet<>();
        Set<String> monthlyDates = new HashSet<>();

        for (CheckInRecord r : records) {
            String date = r.getDate();
            if (DateUtil.isCurrentWeek(date)) {
                weeklyDates.add(date);
            }
            if (DateUtil.isCurrentMonth(date)) {
                monthlyDates.add(date);
            }
        }

        weeklyLabel.setText(weeklyDates.size() + " 天");
        monthlyLabel.setText(monthlyDates.size() + " 天");
        taskCountLabel.setText(taskCount + " 个");
    }
}
