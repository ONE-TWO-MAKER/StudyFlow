package org.example.ui.frame;

import org.example.ui.panel.CheckInPanel;
import org.example.ui.panel.HomePanel;
import org.example.ui.panel.StatisticsPanel;
import org.example.ui.panel.TaskPanel;

import javax.swing.*;
import java.awt.*;

/**
 * 主窗口，左侧菜单栏 + 右侧 CardLayout 内容区。
 */
public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    private JButton homeBtn;
    private JButton taskBtn;
    private JButton checkInBtn;
    private JButton statisticsBtn;

    private HomePanel homePanel;
    private TaskPanel taskPanel;
    private CheckInPanel checkInPanel;
    private StatisticsPanel statisticsPanel;

    private static final Color SIDEBAR_BG = new Color(44, 62, 80);
    private static final Color BTN_NORMAL = new Color(44, 62, 80);
    private static final Color BTN_HOVER = new Color(52, 73, 94);
    private static final Color BTN_ACTIVE = new Color(26, 188, 156);
    private static final Color BTN_TEXT = new Color(236, 240, 241);

    public MainFrame() {
        setTitle("StudyFlow - 学习打卡系统");
        setSize(960, 660);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initPanels();
        createSidebar();
        createContentPanel();

        setVisible(true);
    }

    private void initPanels() {
        homePanel = new HomePanel();
        taskPanel = new TaskPanel();
        checkInPanel = new CheckInPanel();
        statisticsPanel = new StatisticsPanel();
    }

    private void createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(180, getHeight()));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel logoLabel = new JLabel("StudyFlow");
        logoLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 22));
        logoLabel.setForeground(BTN_ACTIVE);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        sidebar.add(logoLabel);

        homeBtn = createMenuButton("首  页", true);
        taskBtn = createMenuButton("任务管理", false);
        checkInBtn = createMenuButton("学习打卡", false);
        statisticsBtn = createMenuButton("数据统计", false);

        sidebar.add(homeBtn);
        sidebar.add(taskBtn);
        sidebar.add(checkInBtn);
        sidebar.add(statisticsBtn);

        sidebar.add(Box.createVerticalGlue());

        // 退出登录按钮
        JButton logoutBtn = new JButton("退出登录");
        logoutBtn.setFont(new Font("Microsoft YaHei", Font.PLAIN, 15));
        logoutBtn.setForeground(BTN_TEXT);
        logoutBtn.setBackground(SIDEBAR_BG);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        logoutBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutBtn.setForeground(new Color(231, 76, 60));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logoutBtn.setForeground(BTN_TEXT);
            }
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(LoginFrame::new);
        });

        sidebar.add(logoutBtn);
        sidebar.add(Box.createVerticalStrut(15));

        add(sidebar, BorderLayout.WEST);
    }

    private JButton createMenuButton(String text, boolean isActive) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Microsoft YaHei", Font.PLAIN, 15));
        btn.setForeground(BTN_TEXT);
        btn.setBackground(isActive ? BTN_ACTIVE : BTN_NORMAL);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (btn.getBackground() != BTN_ACTIVE) {
                    btn.setBackground(BTN_HOVER);
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btn.getBackground() != BTN_ACTIVE) {
                    btn.setBackground(BTN_NORMAL);
                }
            }
        });

        return btn;
    }

    private void createContentPanel() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.WHITE);

        contentPanel.add(homePanel, "home");
        contentPanel.add(taskPanel, "task");
        contentPanel.add(checkInPanel, "checkin");
        contentPanel.add(statisticsPanel, "statistics");

        cardLayout.show(contentPanel, "home");

        homeBtn.addActionListener(e -> switchPage("home", homeBtn));
        taskBtn.addActionListener(e -> switchPage("task", taskBtn));
        checkInBtn.addActionListener(e -> switchPage("checkin", checkInBtn));
        statisticsBtn.addActionListener(e -> switchPage("statistics", statisticsBtn));

        add(contentPanel, BorderLayout.CENTER);
    }

    private void switchPage(String pageName, JButton activeBtn) {
        cardLayout.show(contentPanel, pageName);
        resetMenuButtons();
        activeBtn.setBackground(BTN_ACTIVE);
    }

    private void resetMenuButtons() {
        JButton[] buttons = {homeBtn, taskBtn, checkInBtn, statisticsBtn};
        for (JButton btn : buttons) {
            btn.setBackground(BTN_NORMAL);
        }
    }
}
