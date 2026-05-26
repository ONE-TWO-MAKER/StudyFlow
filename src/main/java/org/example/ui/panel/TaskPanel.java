package org.example.ui.panel;

import org.example.ui.dialog.AddTaskDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 任务面板，使用 JTable 展示任务列表，支持右键菜单编辑和删除任务。
 */
public class TaskPanel extends JPanel {

    private JTable taskTable;
    private DefaultTableModel tableModel;

    private static final Color ACCENT = new Color(26, 188, 156);
    private static final Color DARK_BG = new Color(44, 62, 80);
    private static final Color SELECTION_BG = new Color(230, 246, 243);
    private static final Color SELECTION_FG = new Color(44, 62, 80);

    public TaskPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 顶部标题栏
        add(createTopPanel(), BorderLayout.NORTH);

        // 表格区域
        add(createTablePanel(), BorderLayout.CENTER);
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

        // 点击添加按钮，弹出对话框添加新任务
        addBtn.addActionListener(e -> {
            Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
            AddTaskDialog dialog = new AddTaskDialog(owner);
            dialog.setVisible(true);

            if (dialog.isConfirmed()) {
                tableModel.addRow(new Object[]{dialog.getTaskName(), dialog.getDuration() + " 分钟"});
            }
        });

        return topPanel;
    }

    /**
     * 创建表格面板，包含 JTable 和右键菜单
     */
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));

        // 表格列名
        String[] columns = {"任务名称", "学习时长"};
        tableModel = new DefaultTableModel(columns, 0) {
            // 禁止直接编辑单元格
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        taskTable = new JTable(tableModel);
        styleTable();

        // 右键菜单
        JPopupMenu popupMenu = createPopupMenu();

        // 鼠标事件：右键弹出菜单
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
                    // 右键时自动选中该行
                    taskTable.setRowSelectionInterval(row, row);
                }
                // 仅当有选中行时才显示菜单
                if (taskTable.getSelectedRow() >= 0) {
                    popupMenu.show(taskTable, e.getX(), e.getY());
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(taskTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 223, 230)));
        scrollPane.setBackground(Color.WHITE);

        panel.add(scrollPane, BorderLayout.CENTER);

        // 空数据提示
        JLabel hintLabel = new JLabel("暂无任务，点击右上角按钮添加", JLabel.CENTER);
        hintLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        hintLabel.setForeground(new Color(189, 195, 199));
        hintLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panel.add(hintLabel, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * 美化 JTable：行高、字体、表头样式、选中颜色
     */
    private void styleTable() {
        // 行高
        taskTable.setRowHeight(40);

        // 表格字体
        taskTable.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        taskTable.setForeground(DARK_BG);
        taskTable.setGridColor(new Color(230, 232, 235));
        taskTable.setShowVerticalLines(false);
        taskTable.setIntercellSpacing(new Dimension(10, 0));

        // 选中颜色
        taskTable.setSelectionBackground(SELECTION_BG);
        taskTable.setSelectionForeground(SELECTION_FG);

        // 表头样式
        JTableHeader header = taskTable.getTableHeader();
        header.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
        header.setBackground(new Color(245, 247, 250));
        header.setForeground(DARK_BG);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 38));

        // 表头不可拖拽
        header.setReorderingAllowed(false);

        // 表格背景
        taskTable.setBackground(Color.WHITE);

        // 列内容居中
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        taskTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
    }

    /**
     * 创建右键弹出菜单：编辑任务、删除任务
     */
    private JPopupMenu createPopupMenu() {
        JPopupMenu menu = new JPopupMenu();
        menu.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));

        // 编辑任务菜单项
        JMenuItem editItem = new JMenuItem("编辑任务");
        editItem.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        editItem.addActionListener(e -> editSelectedTask());

        // 删除任务菜单项
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
     * 编辑选中行：打开对话框并预填充数据，确认后更新表格
     */
    private void editSelectedTask() {
        int row = taskTable.getSelectedRow();
        if (row < 0) return;

        // 获取当前行数据
        String name = (String) tableModel.getValueAt(row, 0);
        String durationStr = (String) tableModel.getValueAt(row, 1);
        // 从"XX 分钟"中提取数字
        int duration = Integer.parseInt(durationStr.replace(" 分钟", ""));

        // 打开编辑对话框，预填充数据
        Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
        AddTaskDialog dialog = new AddTaskDialog(owner, name, duration);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            // 更新表格中对应行
            tableModel.setValueAt(dialog.getTaskName(), row, 0);
            tableModel.setValueAt(dialog.getDuration() + " 分钟", row, 1);
        }
    }

    /**
     * 删除选中行：弹出确认提示，确认后从表格中移除
     */
    private void deleteSelectedTask() {
        int row = taskTable.getSelectedRow();
        if (row < 0) return;

        String name = (String) tableModel.getValueAt(row, 0);

        // 删除前弹出确认对话框
        int result = JOptionPane.showConfirmDialog(
                this,
                "确定要删除任务 \"" + name + "\" 吗？\n删除后不可恢复。",
                "确认删除",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (result == JOptionPane.YES_OPTION) {
            tableModel.removeRow(row);
        }
    }
}
