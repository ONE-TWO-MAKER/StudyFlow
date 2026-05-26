package org.example.model;

/**
 * 任务实体类，表示用户创建的学习任务。
 */
public class Task {
    /**
     * 任务ID
     */
    private int id;

    /**
     * 任务名称
     */
    private String title;

    /**
     * 学习科目
     */
    private String subject;

    /**
     * 学习时长（分钟）
     */
    private int duration;

    /**
     * 任务是否完成
     */
    private boolean completed;

}
