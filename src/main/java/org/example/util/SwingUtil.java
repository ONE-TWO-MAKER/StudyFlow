package org.example.util;

import javax.swing.*;
import java.awt.*;

/**
 * Swing工具类，提供界面布局、组件样式等GUI相关的静态方法。
 */
public final class SwingUtil {

    private SwingUtil() {
        // 工具类，禁止实例化
    }

    /**
     * 弹出错误警告框。
     *
     * @param parent  父组件，可为 null
     * @param message 错误信息
     */
    public static void showErrorDialog(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "错误", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * 弹出成功提示框。
     *
     * @param parent  父组件，可为 null
     * @param message 提示信息
     */
    public static void showInfoDialog(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "提示", JOptionPane.INFORMATION_MESSAGE);
    }
}
