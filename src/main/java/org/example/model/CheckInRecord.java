package org.example.model;

/**
 * 打卡记录实体类，记录用户每次打卡的详细信息。
 */
public class CheckInRecord {
    /**
     * 打卡记录ID
     */
    private int id;

    /**
     * 打卡日期
     */
    private String date;

    /**
     * 对应任务名称
     */
    private String taskName;

    /**
     * 学习时长（分钟）
     */
    private int studyTime;
}
