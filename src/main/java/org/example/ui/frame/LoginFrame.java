package org.example.ui.frame;

import org.example.dao.impl.UserDaoImpl;
import org.example.model.User;
import org.example.service.impl.UserServiceImpl;
import org.example.util.SwingUtil;

import javax.swing.*;
import java.awt.*;

/**
 * 登录窗口，提供用户登录和注册的图形界面。
 */
public class LoginFrame extends JFrame {

    private final UserServiceImpl userService;
    private JTextField usernameField;
    private JPasswordField passwordField;

    private static final Color BG_COLOR = new Color(44, 62, 80);
    private static final Color ACCENT_COLOR = new Color(26, 188, 156);
    private static final Color FIELD_BG = new Color(236, 240, 241);
    private static final Font LABEL_FONT = new Font("Microsoft YaHei", Font.PLAIN, 14);
    private static final Font TITLE_FONT = new Font("Microsoft YaHei", Font.BOLD, 24);
    private static final Font BTN_FONT = new Font("Microsoft YaHei", Font.BOLD, 14);

    public LoginFrame() {
        userService = new UserServiceImpl(new UserDaoImpl());

        setTitle("StudyFlow - 登录");
        setSize(400, 340);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        initUI();

        setVisible(true);
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 30, 6, 30);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 标题
        JLabel titleLabel = new JLabel("StudyFlow", SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(ACCENT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // 用户名
        JLabel userLabel = new JLabel("用户名");
        userLabel.setFont(LABEL_FONT);
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(userLabel, gbc);

        usernameField = new JTextField(15);
        usernameField.setFont(LABEL_FONT);
        usernameField.setBackground(FIELD_BG);
        usernameField.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
        gbc.gridx = 1;
        mainPanel.add(usernameField, gbc);

        // 密码
        JLabel passLabel = new JLabel("密  码");
        passLabel.setFont(LABEL_FONT);
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(passLabel, gbc);

        passwordField = new JPasswordField(15);
        passwordField.setFont(LABEL_FONT);
        passwordField.setBackground(FIELD_BG);
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);

        // 按钮面板
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.setBackground(Color.WHITE);

        JButton loginBtn = createButton("登  录", ACCENT_COLOR);
        JButton registerBtn = createButton("注  册", new Color(52, 152, 219));

        loginBtn.addActionListener(e -> handleLogin());
        registerBtn.addActionListener(e -> handleRegister());

        btnPanel.add(loginBtn);
        btnPanel.add(registerBtn);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 30, 6, 30);
        mainPanel.add(btnPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(BTN_FONT);
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        try {
            User user = userService.login(username, password);
            dispose();
            new MainFrame(user);
        } catch (RuntimeException e) {
            SwingUtil.showErrorDialog(this, e.getMessage());
        }
    }

    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        try {
            userService.register(user);
            SwingUtil.showInfoDialog(this, "注册成功，请登录");
            passwordField.setText("");
        } catch (RuntimeException e) {
            SwingUtil.showErrorDialog(this, e.getMessage());
        }
    }
}
