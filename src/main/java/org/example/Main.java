package org.example;

import org.example.ui.frame.MainFrame;

import javax.swing.*;

/**
 * 程序入口类，负责启动学习打卡系统应用。
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
