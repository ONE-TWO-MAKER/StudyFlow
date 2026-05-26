package org.example.ui.dialog;

import javax.swing.*;
import java.awt.*;

/**
 * 添加/编辑任务对话框，用于创建新任务或编辑已有任务的弹窗界面。
 */
public class AddTaskDialog extends JDialog {

    private JTextField nameField;
    private JTextField durationField;
    private JLabel headerLabel;
    private boolean confirmed = false;
    private boolean editMode = false;

    private static final Color ACCENT = new Color(26, 188, 156);
    private static final Color DARK_BG = new Color(44, 62, 80);
    private static final Color FG_COLOR = new Color(44, 62, 80);
    private static final Color BORDER_COLOR = new Color(220, 223, 230);

    /**
     * 添加模式构造方法
     */
    public AddTaskDialog(Frame owner) {
        super(owner, "添加新任务", true);
        initUI();
    }

    /**
     * 编辑模式构造方法，自动填充已有任务数据
     */
    public AddTaskDialog(Frame owner, String taskName, int duration) {
        super(owner, "编辑任务", true);
        this.editMode = true;
        initUI();
        // 预填充数据
        nameField.setText(taskName);
        durationField.setText(String.valueOf(duration));
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

        // 标题，编辑模式下显示"编辑任务"
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        String title = editMode ? "编辑学习任务" : "新建学习任务";
        headerLabel = new JLabel(title, JLabel.CENTER);
        headerLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 18));
        headerLabel.setForeground(DARK_BG);
        panel.add(headerLabel, gbc);

        // 分隔线
        gbc.gridy = 1;
        gbc.insets = new Insets(15, 0, 20, 0);
        JSeparator separator = new JSeparator();
        separator.setForeground(BORDER_COLOR);
        panel.add(separator, gbc);

        // 任务名称
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 0, 8, 10);
        JLabel nameLabel = new JLabel("任务名称：");
        nameLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        nameLabel.setForeground(FG_COLOR);
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.weightx = 1.0;
        nameField = new JTextField();
        nameField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));
        panel.add(nameField, gbc);

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

        // 提示文字
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(3, 0, 0, 0);
        JLabel hintLabel = new JLabel("时长单位为分钟", JLabel.RIGHT);
        hintLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        hintLabel.setForeground(new Color(189, 195, 199));
        panel.add(hintLabel, gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR));

        JButton confirmBtn = new JButton("确定");
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

    private boolean validateForm() {
        String name = nameField.getText().trim();
        String duration = durationField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入任务名称", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (duration.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入学习时长", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            int d = Integer.parseInt(duration);
            if (d <= 0) {
                JOptionPane.showMessageDialog(this, "学习时长必须大于0", "提示", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "学习时长请输入数字", "提示", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * 是否点击了确定按钮
     */
    public boolean isConfirmed() {
        return confirmed;
    }

    /**
     * 获取用户输入的任务名称
     */
    public String getTaskName() {
        return nameField.getText().trim();
    }

    /**
     * 获取用户输入的学习时长（分钟）
     */
    public int getDuration() {
        return Integer.parseInt(durationField.getText().trim());
    }
}
