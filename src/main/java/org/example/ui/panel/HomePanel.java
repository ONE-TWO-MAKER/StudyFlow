package org.example.ui.panel;

import org.example.model.Task;
import org.example.model.User;
import org.example.service.CheckInService;
import org.example.service.TaskService;

import javax.swing.*;
import java.awt.*;

/**
 * 首页面板，展示用户概览信息和今日任务摘要。
 */
public class HomePanel extends JPanel {

    private final User currentUser;
    private final TaskService taskService;
    private final CheckInService checkInService;

    private JLabel taskCountLabel;
    private JLabel completedLabel;
    private JLabel streakLabel;
    private JLabel totalCheckInLabel;

    public HomePanel(User currentUser, TaskService taskService, CheckInService checkInService) {
        this.currentUser = currentUser;
        this.taskService = taskService;
        this.checkInService = checkInService;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("欢迎使用 StudyFlow 学习打卡系统", JLabel.CENTER);
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 22));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));
        infoPanel.setBackground(Color.WHITE);

        infoPanel.add(createInfoCard("今日任务", "0 项"));
        infoPanel.add(createInfoCard("已完成", "0 项"));
        infoPanel.add(createInfoCard("连续打卡", "0 天"));
        infoPanel.add(createInfoCard("总打卡数", "0 次"));

        add(infoPanel, BorderLayout.CENTER);
    }

    private JPanel createInfoCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(245, 247, 250));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 223, 230), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(127, 140, 141));

        JLabel valueLabel = new JLabel(value, JLabel.CENTER);
        valueLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 28));
        valueLabel.setForeground(new Color(44, 62, 80));

        // 保存引用以便 loadData 更新
        switch (title) {
            case "今日任务":
                taskCountLabel = valueLabel;
                break;
            case "已完成":
                completedLabel = valueLabel;
                break;
            case "连续打卡":
                streakLabel = valueLabel;
                break;
            case "总打卡数":
                totalCheckInLabel = valueLabel;
                break;
        }

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    /**
     * 从数据库加载最新数据并更新首页统计卡片。
     */
    public void loadData() {
        int taskCount = taskService.getAllTasks(currentUser.getId()).size();
        int totalCheckIns = checkInService.getAllRecords(currentUser.getId()).size();
        int totalStudyTime = checkInService.getTotalStudyTime(currentUser.getId());

        taskCountLabel.setText(taskCount + " 项");
        long completedCount = taskService.getAllTasks(currentUser.getId()).stream()
                .filter(Task::isCompleted)
                .count();
        completedLabel.setText(completedCount + " 项");
        streakLabel.setText("0 天");
        totalCheckInLabel.setText(totalCheckIns + " 次");
    }
}
