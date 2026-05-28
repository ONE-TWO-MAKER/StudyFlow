package org.example.dao.impl;

import org.example.dao.TaskDao;
import org.example.model.Task;
import org.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDaoImpl implements TaskDao {

    @Override
    public void insert(Task task) {
        String sql = "INSERT INTO tasks (user_id, title, subject, duration, completed) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, task.getUserId());
            ps.setString(2, task.getTitle());
            ps.setString(3, task.getSubject());
            ps.setInt(4, task.getDuration());
            ps.setInt(5, task.isCompleted() ? 1 : 0);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert task: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete task: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Task task) {
        String sql = "UPDATE tasks SET user_id = ?, title = ?, subject = ?, duration = ?, completed = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, task.getUserId());
            ps.setString(2, task.getTitle());
            ps.setString(3, task.getSubject());
            ps.setInt(4, task.getDuration());
            ps.setInt(5, task.isCompleted() ? 1 : 0);
            ps.setInt(6, task.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update task: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Task> selectAll() {
        String sql = "SELECT id, user_id, title, subject, duration, completed FROM tasks";
        List<Task> tasks = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setUserId(rs.getInt("user_id"));
                task.setTitle(rs.getString("title"));
                task.setSubject(rs.getString("subject"));
                task.setDuration(rs.getInt("duration"));
                task.setCompleted(rs.getInt("completed") == 1);
                tasks.add(task);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to select all tasks: " + e.getMessage(), e);
        }
        return tasks;
    }
}
