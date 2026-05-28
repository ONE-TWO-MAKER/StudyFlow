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
     * @throws RuntimeException 校验失败时抛出
     */
    void addRecord(CheckInRecord record);

    /**
     * 获取指定用户的所有打卡记录
     *
     * @param userId 用户ID
     * @return 打卡记录列表
     */
    List<CheckInRecord> getAllRecords(int userId);

    /**
     * 获取指定用户的总学习时长
     *
     * @param userId 用户ID
     * @return 学习总时长（分钟）
     */
    int getTotalStudyTime(int userId);
}
