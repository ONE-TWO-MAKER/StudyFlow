package org.example.model;

/**
 * 任务实体类，表示用户创建的学习任务。
 */
public class Task {
    private int id;
    private int userId;
    private String title;
    private String subject;
    private int duration;
    private boolean completed;

    public Task() {
    }

    public Task(int id, int userId, String title, String subject, int duration, boolean completed) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.subject = subject;
        this.duration = duration;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", subject='" + subject + '\'' +
                ", duration=" + duration +
                ", completed=" + completed +
                '}';
    }
}
