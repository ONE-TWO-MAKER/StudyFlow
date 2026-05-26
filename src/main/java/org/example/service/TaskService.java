package org.example.service;

import org.example.model.Task;

import java.util.List;

/**
 * 任务服务接口，定义任务相关的业务操作规范。
 */
public interface TaskService {
    /**
     * 添加任务
     *
     * @param task 学习任务对象
     */
    void addTask(Task task);

    /**
     * 删除任务
     *
     * @param id 任务ID
     */
    void deleteTask(int id);

    /**
     * 修改任务
     *
     * @param task 学习任务对象
     */
    void updateTask(Task task);

    /**
     * 获取所有任务
     *
     * @return 任务列表
     */
    List<Task> getAllTasks();
}
