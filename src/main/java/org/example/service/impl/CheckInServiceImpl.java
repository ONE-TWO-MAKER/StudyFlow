package org.example.service.impl;

import org.example.dao.CheckInDao;
import org.example.model.CheckInRecord;
import org.example.service.CheckInService;

import java.util.List;
import java.util.stream.Collectors;

public class CheckInServiceImpl implements CheckInService {

    private final CheckInDao checkInDao;

    public CheckInServiceImpl(CheckInDao checkInDao) {
        this.checkInDao = checkInDao;
    }

    @Override
    public void addRecord(CheckInRecord record) {
        if (record.getDate() == null || record.getDate().trim().isEmpty()) {
            throw new RuntimeException("打卡日期不能为空");
        }
        if (record.getTaskName() == null || record.getTaskName().trim().isEmpty()) {
            throw new RuntimeException("任务名称不能为空");
        }
        if (record.getStudyTime() <= 0) {
            throw new RuntimeException("打卡时长必须大于0");
        }
        record.setTaskName(record.getTaskName().trim());
        checkInDao.insert(record);
    }

    @Override
    public List<CheckInRecord> getAllRecords(int userId) {
        return checkInDao.selectAll().stream()
                .filter(record -> record.getUserId() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public int getTotalStudyTime(int userId) {
        return checkInDao.getTotalStudyTime(userId);
    }
}
