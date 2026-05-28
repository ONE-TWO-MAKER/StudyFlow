package org.example;

import org.example.ui.frame.LoginFrame;
import org.example.util.DBUtil;

import javax.swing.*;

/**
 * 程序入口类，负责启动学习打卡系统应用。
 */
public class Main {
    public static void main(String[] args) {
        DBUtil.initDatabase();

        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
