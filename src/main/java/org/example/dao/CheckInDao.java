package org.example.dao;

import org.example.model.CheckInRecord;

import java.util.List;

/**
 * 打卡记录数据访问接口
 * 用于定义打卡记录相关的数据库操作方法
 */
public interface CheckInDao {

    /**
     * 向数据库中插入打卡记录
     *
     * @param record 打卡记录对象
     * @return 插入成功返回 true，否则返回 false
     */
    boolean insert(CheckInRecord record);

    /**
     * 查询所有打卡记录
     *
     * @return 打卡记录集合
     */
    List<CheckInRecord> selectAll();

    /**
     * 根据日期查询打卡记录
     *
     * @param date 日期字符串 (yyyy-MM-dd)
     * @return 该日期的打卡记录集合
     */
    List<CheckInRecord> selectByDate(String date);

    /**
     * 获取指定用户的总学习时长
     *
     * @param userId 用户ID
     * @return 总学习时长（分钟）
     */
    int getTotalStudyTime(int userId);

    /**
     * 根据记录ID删除打卡记录
     *
     * @param id 打卡记录ID
     * @return 删除成功返回 true，否则返回 false
     */
    boolean delete(int id);
}
