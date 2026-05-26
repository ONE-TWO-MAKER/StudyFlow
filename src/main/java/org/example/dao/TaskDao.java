package org.example.dao;

import org.example.model.Task;

import java.util.List;

// ... existing code ...


import java.util.List;

/**
 * 学习任务数据访问接口
 * 用于定义学习任务相关的数据库操作方法
 *
 * DAO（Data Access Object）：
 * 数据访问层，专门负责操作数据库。
 *
 * 该接口主要用于：
 * 1. 添加任务数据
 * 2. 删除任务数据
 * 3. 修改任务数据
 * 4. 查询任务数据
 *
 * 本接口只负责定义方法，
 * 不负责具体实现。
 */
public interface TaskDao {

    /**
     * 向数据库中插入学习任务
     *
     * @param task 学习任务对象
     */
    void insert(Task task);

    /**
     * 根据任务ID删除任务
     *
     * @param id 任务ID
     */
    void delete(int id);

    /**
     * 修改任务信息
     *
     * @param task 学习任务对象
     */
    void update(Task task);

    /**
     * 查询所有学习任务
     *
     * @return 学习任务集合
     */
    List<Task> selectAll();

}