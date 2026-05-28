package org.example.service.impl;

import org.example.dao.TaskDao;
import org.example.model.Task;
import org.example.service.TaskService;

import java.util.List;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService {

    private final TaskDao taskDao;

    public TaskServiceImpl(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public void addTask(Task task) {
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new RuntimeException("任务名称不能为空");
        }
        if (task.getDuration() <= 0) {
            throw new RuntimeException("学习时长必须大于0");
        }
        task.setTitle(task.getTitle().trim());
        taskDao.insert(task);
    }

    @Override
    public void deleteTask(int id) {
        if (id <= 0) {
            throw new RuntimeException("无效的任务ID");
        }
        taskDao.delete(id);
    }

    @Override
    public void updateTask(Task task) {
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new RuntimeException("任务名称不能为空");
        }
        if (task.getDuration() <= 0) {
            throw new RuntimeException("学习时长必须大于0");
        }
        task.setTitle(task.getTitle().trim());
        taskDao.update(task);
    }

    @Override
    public List<Task> getAllTasks(int userId) {
        return taskDao.selectAll().stream()
                .filter(task -> task.getUserId() == userId)
                .collect(Collectors.toList());
    }
}
