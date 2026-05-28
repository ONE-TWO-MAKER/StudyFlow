package org.example.dao.impl;

import org.example.dao.CheckInDao;
import org.example.model.CheckInRecord;
import org.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CheckInDaoImpl implements CheckInDao {

    @Override
    public boolean insert(CheckInRecord record) {
        String sql = "INSERT INTO check_in_records (user_id, date, task_name, study_time) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, record.getUserId());
            ps.setString(2, record.getDate());
            ps.setString(3, record.getTaskName());
            ps.setInt(4, record.getStudyTime());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert check-in record: " + e.getMessage(), e);
        }
    }

    @Override
    public List<CheckInRecord> selectAll() {
        String sql = "SELECT id, user_id, date, task_name, study_time FROM check_in_records";
        List<CheckInRecord> records = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                records.add(mapRecord(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to select all check-in records: " + e.getMessage(), e);
        }
        return records;
    }

    @Override
    public List<CheckInRecord> selectByDate(String date) {
        String sql = "SELECT id, user_id, date, task_name, study_time FROM check_in_records WHERE date = ?";
        List<CheckInRecord> records = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, date);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    records.add(mapRecord(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to select check-in records by date: " + e.getMessage(), e);
        }
        return records;
    }

    @Override
    public int getTotalStudyTime(int userId) {
        String sql = "SELECT COALESCE(SUM(study_time), 0) FROM check_in_records WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get total study time: " + e.getMessage(), e);
        }
        return 0;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM check_in_records WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete check-in record: " + e.getMessage(), e);
        }
    }

    private CheckInRecord mapRecord(ResultSet rs) throws SQLException {
        CheckInRecord record = new CheckInRecord();
        record.setId(rs.getInt("id"));
        record.setUserId(rs.getInt("user_id"));
        record.setDate(rs.getString("date"));
        record.setTaskName(rs.getString("task_name"));
        record.setStudyTime(rs.getInt("study_time"));
        return record;
    }
}
