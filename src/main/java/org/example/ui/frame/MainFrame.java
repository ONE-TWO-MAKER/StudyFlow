package org.example.ui.frame;

import org.example.dao.impl.CheckInDaoImpl;
import org.example.dao.impl.TaskDaoImpl;
import org.example.model.User;
import org.example.service.CheckInService;
import org.example.service.TaskService;
import org.example.service.impl.CheckInServiceImpl;
import org.example.service.impl.TaskServiceImpl;
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

    private final User currentUser;
    private final TaskService taskService;
    private final CheckInService checkInService;

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

    public MainFrame(User currentUser) {
        this.currentUser = currentUser;
        this.taskService = new TaskServiceImpl(new TaskDaoImpl());
        this.checkInService = new CheckInServiceImpl(new CheckInDaoImpl());

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
        homePanel = new HomePanel(currentUser, taskService, checkInService);
        taskPanel = new TaskPanel(currentUser, taskService);
        checkInPanel = new CheckInPanel(currentUser, taskService, checkInService);
        statisticsPanel = new StatisticsPanel(currentUser, taskService, checkInService);
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

        // 每次切换页面时刷新数据，确保显示最新状态
        switch (pageName) {
            case "home":
                homePanel.loadData();
                break;
            case "task":
                taskPanel.loadTaskData();
                break;
            case "checkin":
                checkInPanel.loadData();
                break;
            case "statistics":
                statisticsPanel.loadData();
                break;
        }

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
