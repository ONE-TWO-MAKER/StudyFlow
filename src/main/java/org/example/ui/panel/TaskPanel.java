package org.example.ui.panel;

import org.example.model.Task;
import org.example.model.User;
import org.example.service.TaskService;
import org.example.ui.dialog.AddTaskDialog;
import org.example.util.SwingUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 任务面板，使用 JTable 展示任务列表，支持右键菜单编辑和删除任务。
 * 数据通过 TaskService 与数据库同步。
 */
public class TaskPanel extends JPanel {

    private final User currentUser;
    private final TaskService taskService;

    private JTable taskTable;
    private DefaultTableModel tableModel;

    private static final Color ACCENT = new Color(26, 188, 156);
    private static final Color DARK_BG = new Color(44, 62, 80);
    private static final Color SELECTION_BG = new Color(230, 246, 243);
    private static final Color SELECTION_FG = new Color(44, 62, 80);

    public TaskPanel(User currentUser, TaskService taskService) {
        this.currentUser = currentUser;
        this.taskService = taskService;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(createTopPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);

        loadTaskData();
    }

    /**
     * 从数据库加载当前用户的任务数据到表格。
     */
    public void loadTaskData() {
        tableModel.setRowCount(0);
        for (Task task : taskService.getAllTasks(currentUser.getId())) {
            tableModel.addRow(new Object[]{
                    task.getId(),
                    task.getTitle(),
                    task.getDuration() + " 分钟"
            });
        }
    }

    /**
     * 创建顶部标题栏，包含标题和添加按钮
     */
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        JLabel titleLabel = new JLabel("任务管理");
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 20));
        titleLabel.setForeground(DARK_BG);
        topPanel.add(titleLabel, BorderLayout.WEST);

        JButton addBtn = new JButton("+ 添加任务");
        addBtn.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        addBtn.setBackground(ACCENT);
        addBtn.setForeground(Color.WHITE);
        addBtn.setBorderPainted(false);
        addBtn.setFocusPainted(false);
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        topPanel.add(addBtn, BorderLayout.EAST);

        addBtn.addActionListener(e -> handleAddTask());

        return topPanel;
    }

    /**
     * 处理添加任务：弹出对话框，通过 Service 写入数据库并刷新表格。
     */
    private void handleAddTask() {
        Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
        AddTaskDialog dialog = new AddTaskDialog(owner);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            try {
                Task task = new Task(0, currentUser.getId(),
                        dialog.getTaskName(), "默认科目",
                        dialog.getDuration(), false);
                taskService.addTask(task);
                loadTaskData();
            } catch (RuntimeException e) {
                SwingUtil.showErrorDialog(this, e.getMessage());
            }
        }
    }

    /**
     * 创建表格面板，包含 JTable 和右键菜单
     */
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));

        // 表格列名 — 第 0 列 ID 将被隐藏
        String[] columns = {"ID", "任务名称", "学习时长"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        taskTable = new JTable(tableModel);
        styleTable();

        // 右键菜单
        JPopupMenu popupMenu = createPopupMenu();

        taskTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopup(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopup(e);
                }
            }

            private void showPopup(MouseEvent e) {
                int row = taskTable.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    taskTable.setRowSelectionInterval(row, row);
                }
                if (taskTable.getSelectedRow() >= 0) {
                    popupMenu.show(taskTable, e.getX(), e.getY());
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(taskTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 223, 230)));
        scrollPane.setBackground(Color.WHITE);

        panel.add(scrollPane, BorderLayout.CENTER);

        JLabel hintLabel = new JLabel("暂无任务，点击右上角按钮添加", JLabel.CENTER);
        hintLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        hintLabel.setForeground(new Color(189, 195, 199));
        hintLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panel.add(hintLabel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * 美化 JTable：行高、字体、表头样式、选中颜色，并隐藏第 0 列（ID）。
     */
    private void styleTable() {
        taskTable.setRowHeight(40);

        taskTable.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        taskTable.setForeground(DARK_BG);
        taskTable.setGridColor(new Color(230, 232, 235));
        taskTable.setShowVerticalLines(false);
        taskTable.setIntercellSpacing(new Dimension(10, 0));

        taskTable.setSelectionBackground(SELECTION_BG);
        taskTable.setSelectionForeground(SELECTION_FG);

        JTableHeader header = taskTable.getTableHeader();
        header.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
        header.setBackground(new Color(245, 247, 250));
        header.setForeground(DARK_BG);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 38));
        header.setReorderingAllowed(false);

        taskTable.setBackground(Color.WHITE);

        // 隐藏 ID 列
        taskTable.getColumnModel().getColumn(0).setMinWidth(0);
        taskTable.getColumnModel().getColumn(0).setMaxWidth(0);
        taskTable.getColumnModel().getColumn(0).setPreferredWidth(0);

        // 内容列居中
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        taskTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
    }

    /**
     * 创建右键弹出菜单：编辑任务、删除任务
     */
    private JPopupMenu createPopupMenu() {
        JPopupMenu menu = new JPopupMenu();
        menu.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));

        JMenuItem editItem = new JMenuItem("编辑任务");
        editItem.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        editItem.addActionListener(e -> editSelectedTask());

        JMenuItem deleteItem = new JMenuItem("删除任务");
        deleteItem.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        deleteItem.setForeground(new Color(231, 76, 60));
        deleteItem.addActionListener(e -> deleteSelectedTask());

        menu.add(editItem);
        menu.addSeparator();
        menu.add(deleteItem);

        return menu;
    }

    /**
     * 编辑选中行：获取隐藏的任务 ID，弹出对话框修改，通过 Service 更新数据库。
     */
    private void editSelectedTask() {
        int row = taskTable.getSelectedRow();
        if (row < 0) return;

        int taskId = (int) tableModel.getValueAt(row, 0);
        String name = (String) tableModel.getValueAt(row, 1);
        String durationStr = (String) tableModel.getValueAt(row, 2);
        int duration = Integer.parseInt(durationStr.replace(" 分钟", ""));

        Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
        AddTaskDialog dialog = new AddTaskDialog(owner, name, duration);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            try {
                Task task = new Task(taskId, currentUser.getId(),
                        dialog.getTaskName(), "默认科目",
                        dialog.getDuration(), false);
                taskService.updateTask(task);
                loadTaskData();
            } catch (RuntimeException e) {
                SwingUtil.showErrorDialog(this, e.getMessage());
            }
        }
    }

    /**
     * 删除选中行：获取隐藏的任务 ID，确认后通过 Service 从数据库移除。
     */
    private void deleteSelectedTask() {
        int row = taskTable.getSelectedRow();
        if (row < 0) return;

        int taskId = (int) tableModel.getValueAt(row, 0);
        String name = (String) tableModel.getValueAt(row, 1);

        int result = JOptionPane.showConfirmDialog(
                this,
                "确定要删除任务 \"" + name + "\" 吗？\n删除后不可恢复。",
                "确认删除",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            try {
                taskService.deleteTask(taskId);
                loadTaskData();
            } catch (RuntimeException e) {
                SwingUtil.showErrorDialog(this, e.getMessage());
            }
        }
    }
}
