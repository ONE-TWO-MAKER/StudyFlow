package org.example.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期工具类，提供日期格式化、解析和计算等静态方法。
 */
public final class DateUtil {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private DateUtil() {
        // 工具类，禁止实例化
    }

    /**
     * 获取当前日期字符串，格式 yyyy-MM-dd。
     */
    public static String getTodayDate() {
        return formatDate(new Date());
    }

    /**
     * 将 Date 对象格式化为 yyyy-MM-dd 字符串。
     */
    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }

    /**
     * 将 yyyy-MM-dd 字符串解析为 Date 对象。
     *
     * @throws RuntimeException 解析失败时抛出
     */
    public static Date parseDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException("日期格式错误，应为 yyyy-MM-dd: " + dateStr, e);
        }
    }

    /**
     * 判断指定日期字符串是否属于当前自然周（周一至周日）。
     *
     * @param dateStr yyyy-MM-dd 格式的日期字符串
     * @return 属于本周返回 true
     */
    public static boolean isCurrentWeek(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr, FORMATTER);
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.with(DayOfWeek.MONDAY);
        LocalDate weekEnd = weekStart.plusDays(6);
        return !date.isBefore(weekStart) && !date.isAfter(weekEnd);
    }

    /**
     * 判断指定日期字符串是否属于当前自然月。
     *
     * @param dateStr yyyy-MM-dd 格式的日期字符串
     * @return 属于本月返回 true
     */
    public static boolean isCurrentMonth(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr, FORMATTER);
        LocalDate today = LocalDate.now();
        return date.getYear() == today.getYear() && date.getMonth() == today.getMonth();
    }
}
