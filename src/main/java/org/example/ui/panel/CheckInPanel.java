package org.example.ui.panel;

import org.example.model.CheckInRecord;
import org.example.model.Task;
import org.example.model.User;
import org.example.service.CheckInService;
import org.example.service.TaskService;
import org.example.ui.dialog.CheckInDialog;
import org.example.util.DateUtil;
import org.example.util.SwingUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * 打卡面板，提供每日学习打卡操作界面。
 */
public class CheckInPanel extends JPanel {

    private final User currentUser;
    private final TaskService taskService;
    private final CheckInService checkInService;

    public CheckInPanel(User currentUser, TaskService taskService, CheckInService checkInService) {
        this.currentUser = currentUser;
        this.taskService = taskService;
        this.checkInService = checkInService;

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

        checkInBtn.addActionListener(e -> handleCheckIn());

        centerPanel.add(checkInBtn);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void handleCheckIn() {
        List<Task> tasks = taskService.getAllTasks(currentUser.getId());

        Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
        CheckInDialog dialog = new CheckInDialog(owner, tasks);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            try {
                CheckInRecord record = new CheckInRecord(0, currentUser.getId(),
                        DateUtil.getTodayDate(),
                        dialog.getSelectedTaskName(),
                        dialog.getStudyTime());
                checkInService.addRecord(record);
                SwingUtil.showInfoDialog(this, "打卡成功！");
            } catch (RuntimeException e) {
                SwingUtil.showErrorDialog(this, e.getMessage());
            }
        }
    }

    /**
     * 切换到此面板时刷新数据（占位，目前无显示数据需要刷新）。
     */
    public void loadData() {
        // 打卡面板为纯交互面板，无需刷新静态数据
    }
}
