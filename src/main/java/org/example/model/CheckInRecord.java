package org.example.model;

/**
 * 打卡记录实体类，记录用户每次打卡的详细信息。
 */
public class CheckInRecord {
    private int id;
    private int userId;
    private String date;
    private String taskName;
    private int studyTime;

    public CheckInRecord() {
    }

    public CheckInRecord(int id, int userId, String date, String taskName, int studyTime) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.taskName = taskName;
        this.studyTime = studyTime;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getStudyTime() {
        return studyTime;
    }

    public void setStudyTime(int studyTime) {
        this.studyTime = studyTime;
    }

    @Override
    public String toString() {
        return "CheckInRecord{" +
                "id=" + id +
                ", userId=" + userId +
                ", date='" + date + '\'' +
                ", taskName='" + taskName + '\'' +
                ", studyTime=" + studyTime +
                '}';
    }
}
