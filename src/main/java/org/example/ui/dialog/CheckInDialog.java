package org.example.ui.dialog;

import org.example.model.Task;
import org.example.util.SwingUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * 打卡对话框，用于选择要打卡的任务和输入实际学习时长。
 */
public class CheckInDialog extends JDialog {

    private final List<Task> tasks;
    private JComboBox<String> taskCombo;
    private JTextField durationField;
    private JButton confirmBtn;
    private boolean confirmed = false;

    private static final Color ACCENT = new Color(26, 188, 156);
    private static final Color DARK_BG = new Color(44, 62, 80);
    private static final Color FG_COLOR = new Color(44, 62, 80);
    private static final Color BORDER_COLOR = new Color(220, 223, 230);

    public CheckInDialog(Frame owner, List<Task> tasks) {
        super(owner, "学习打卡", true);
        this.tasks = tasks;
        initUI();
    }

    private void initUI() {
        setSize(420, 300);
        setLocationRelativeTo(getOwner());
        setResizable(false);
        setLayout(new BorderLayout());

        add(createFormPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);

        // 标题
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel headerLabel = new JLabel("今日打卡", JLabel.CENTER);
        headerLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 18));
        headerLabel.setForeground(DARK_BG);
        panel.add(headerLabel, gbc);

        // 分隔线
        gbc.gridy = 1;
        gbc.insets = new Insets(15, 0, 20, 0);
        JSeparator separator = new JSeparator();
        separator.setForeground(BORDER_COLOR);
        panel.add(separator, gbc);

        if (tasks == null || tasks.isEmpty()) {
            // 无任务时显示提示
            gbc.gridy = 2;
            gbc.insets = new Insets(8, 0, 8, 0);
            JLabel hintLabel = new JLabel("暂无任务，请先去创建任务", JLabel.CENTER);
            hintLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
            hintLabel.setForeground(new Color(189, 195, 199));
            panel.add(hintLabel, gbc);
            return panel;
        }

        // 选择任务
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 0, 8, 10);
        JLabel taskLabel = new JLabel("选择任务：");
        taskLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        taskLabel.setForeground(FG_COLOR);
        panel.add(taskLabel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.weightx = 1.0;
        String[] taskNames = tasks.stream().map(Task::getTitle).toArray(String[]::new);
        taskCombo = new JComboBox<>(taskNames);
        taskCombo.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        taskCombo.addActionListener(e -> prefillDuration());
        panel.add(taskCombo, gbc);

        // 学习时长
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.insets = new Insets(8, 0, 8, 10);
        JLabel durationLabel = new JLabel("学习时长：");
        durationLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        durationLabel.setForeground(FG_COLOR);
        panel.add(durationLabel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.weightx = 1.0;
        durationField = new JTextField();
        durationField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        durationField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        panel.add(durationField, gbc);

        prefillDuration();

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR));

        confirmBtn = new JButton("确定");
        confirmBtn.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        confirmBtn.setBackground(ACCENT);
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setBorderPainted(false);
        confirmBtn.setFocusPainted(false);
        confirmBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confirmBtn.setPreferredSize(new Dimension(100, 35));

        JButton cancelBtn = new JButton("取消");
        cancelBtn.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        cancelBtn.setBackground(Color.WHITE);
        cancelBtn.setForeground(new Color(127, 140, 141));
        cancelBtn.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        cancelBtn.setFocusPainted(false);
        cancelBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelBtn.setPreferredSize(new Dimension(100, 35));

        if (tasks == null || tasks.isEmpty()) {
            confirmBtn.setEnabled(false);
        }

        confirmBtn.addActionListener(e -> {
            if (validateForm()) {
                confirmed = true;
                dispose();
            }
        });

        cancelBtn.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        panel.add(confirmBtn);
        panel.add(cancelBtn);

        return panel;
    }

    private void prefillDuration() {
        if (taskCombo == null || tasks == null) return;
        String selected = (String) taskCombo.getSelectedItem();
        if (selected != null) {
            tasks.stream()
                    .filter(t -> t.getTitle().equals(selected))
                    .findFirst()
                    .ifPresent(t -> durationField.setText(String.valueOf(t.getDuration())));
        }
    }

    /**
     * 校验打卡时长：必须为大于 0 的有效整数。
     * 校验失败时通过 SwingUtil 弹窗提示，不关闭对话框。
     */
    private boolean validateForm() {
        if (durationField == null) return false;
        String text = durationField.getText().trim();
        if (text.isEmpty()) {
            SwingUtil.showErrorDialog(this, "打卡时长请输入大于 0 的有效整数");
            return false;
        }
        try {
            int d = Integer.parseInt(text);
            if (d <= 0) {
                SwingUtil.showErrorDialog(this, "打卡时长请输入大于 0 的有效整数");
                return false;
            }
        } catch (NumberFormatException e) {
            SwingUtil.showErrorDialog(this, "打卡时长请输入大于 0 的有效整数");
            return false;
        }
        return true;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getSelectedTaskName() {
        if (taskCombo == null || taskCombo.getSelectedItem() == null) {
            return "";
        }
        return (String) taskCombo.getSelectedItem();
    }

    public int getStudyTime() {
        if (durationField == null) return 0;
        return Integer.parseInt(durationField.getText().trim());
    }
}
