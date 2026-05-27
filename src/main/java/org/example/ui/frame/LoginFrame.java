package org.example.ui.frame;

import javax.swing.*;
import java.awt.*;

/**
 * 登录窗口，提供用户登录和注册的图形界面。
 * 当前使用假账号验证：admin / 123456
 */
public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private static final Color ACCENT = new Color(26, 188, 156);
    private static final Color DARK_BG = new Color(44, 62, 80);
    private static final Color FIELD_BG = new Color(52, 73, 94);
    private static final Color TEXT_LIGHT = new Color(236, 240, 241);

    public LoginFrame() {
        setTitle("StudyFlow - 登录");
        setSize(500, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // 主面板，深色背景，GridBagLayout 实现垂直居中
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(DARK_BG);
        add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridwidth = 1;

        // ===== 顶部弹簧，配合底部弹簧实现垂直居中 =====
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel topSpacer = new JPanel();
        topSpacer.setOpaque(false);
        mainPanel.add(topSpacer, gbc);

        // ===== Logo =====
        gbc.gridy = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 50, 20, 50);
        JLabel logoLabel = new JLabel("StudyFlow", JLabel.CENTER);
        logoLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 26));
        logoLabel.setForeground(ACCENT);
        mainPanel.add(logoLabel, gbc);

        // ===== 用户名标签 =====
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 50, 5, 50);
        JLabel userLabel = new JLabel("用户名");
        userLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        userLabel.setForeground(TEXT_LIGHT);
        mainPanel.add(userLabel, gbc);

        // ===== 用户名输入框 =====
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 50, 16, 50);
        usernameField = createStyledTextField();
        mainPanel.add(usernameField, gbc);

        // ===== 密码标签 =====
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 50, 5, 50);
        JLabel passLabel = new JLabel("密码");
        passLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        passLabel.setForeground(TEXT_LIGHT);
        mainPanel.add(passLabel, gbc);

        // ===== 密码输入框 =====
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 50, 6, 50);
        passwordField = createStyledPasswordField();
        mainPanel.add(passwordField, gbc);

        // ===== 显示密码复选框 =====
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 50, 18, 50);
        JCheckBox showPwdCheck = createShowPasswordCheck();
        mainPanel.add(showPwdCheck, gbc);

        // ===== 登录按钮（比输入框窄） =====
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 75, 18, 75);
        JButton loginBtn = createLoginButton();
        mainPanel.add(loginBtn, gbc);

        // ===== 注册文字按钮 =====
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 50, 0, 50);
        JButton registerBtn = createRegisterLink();
        mainPanel.add(registerBtn, gbc);

        // ===== 底部弹簧，配合顶部弹簧实现垂直居中 =====
        gbc.gridy = 9;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel bottomSpacer = new JPanel();
        bottomSpacer.setOpaque(false);
        mainPanel.add(bottomSpacer, gbc);

        // 回车键触发登录
        getRootPane().setDefaultButton(loginBtn);

        setVisible(true);
    }

    /**
     * 创建带样式的文本输入框（增高版）
     */
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        field.setForeground(TEXT_LIGHT);
        field.setBackground(FIELD_BG);
        field.setCaretColor(TEXT_LIGHT);
        // 加大上下 padding 让输入框更高
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 90, 110), 1),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        return field;
    }

    /**
     * 创建带样式的密码输入框（增高版）
     */
    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        field.setForeground(TEXT_LIGHT);
        field.setBackground(FIELD_BG);
        field.setCaretColor(TEXT_LIGHT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 90, 110), 1),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)));
        return field;
    }

    /**
     * 创建登录按钮：绿色圆角，比输入框略窄
     */
    private JButton createLoginButton() {
        JButton btn = new JButton("登 录") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // 圆角矩形背景
                g2.setColor(getBackground());
                g2.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Microsoft YaHei", Font.BOLD, 15));
        btn.setBackground(ACCENT);
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 38));

        btn.addActionListener(e -> handleLogin());

        // 鼠标悬停时颜色加深
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(22, 170, 140));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(ACCENT);
            }
        });

        return btn;
    }

    /**
     * 创建注册文字按钮："没有账号？立即注册"
     */
    private JButton createRegisterLink() {
        JButton btn = new JButton("没有账号？立即注册");
        btn.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        btn.setForeground(new Color(160, 170, 180));
        btn.setBackground(DARK_BG);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "注册功能暂未开放，请联系管理员。\n\n测试账号：admin\n测试密码：123456",
                    "提示",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        // 鼠标悬停时变亮
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setForeground(ACCENT);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setForeground(new Color(160, 170, 180));
            }
        });

        return btn;
    }

    /**
     * 创建"显示密码"复选框，勾选时显示真实密码，取消后隐藏
     */
    private JCheckBox createShowPasswordCheck() {
        JCheckBox checkBox = new JCheckBox("显示密码");
        checkBox.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        checkBox.setForeground(new Color(160, 170, 180));
        checkBox.setBackground(DARK_BG);
        checkBox.setFocusPainted(false);
        checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 勾选时显示密码，取消时隐藏
        checkBox.addActionListener(e -> {
            if (checkBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        });

        return checkBox;
    }

    /**
     * 处理登录逻辑：验证用户名密码
     */
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        // 假账号验证
        if ("admin".equals(username) && "123456".equals(password)) {
            // 登录成功，打开主窗口，关闭登录窗口
            SwingUtilities.invokeLater(() -> new MainFrame());
            dispose();
        } else {
            // 登录失败，弹出错误提示
            JOptionPane.showMessageDialog(this,
                    "用户名或密码错误，请重新输入。",
                    "登录失败",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
