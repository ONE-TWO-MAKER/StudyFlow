package org.example.service;

import org.example.model.CheckInRecord;

import java.util.List;

/**
 * 打卡服务接口，定义打卡相关的业务操作规范。
 */
public interface CheckInService {
    /**
     * 添加打卡记录
     *
     * @param record 打卡记录对象
     */
    void addRecord(CheckInRecord record);

    /**
     * 获取所有打卡记录
     *
     * @return 打卡记录列表
     */
    List<CheckInRecord> getAllRecords();

    /**
     * 获取总学习时长
     *
     * @return 学习总时长
     */
    int getTotalStudyTime();

}
