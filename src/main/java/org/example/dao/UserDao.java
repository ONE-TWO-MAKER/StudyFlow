package org.example.dao;

import model.User;

import java.util.List;

/**
 * 用户数据访问接口
 * 用于定义用户相关的数据库操作方法
 *
 * DAO（Data Access Object）：
 * 数据访问层，专门负责操作数据库。
 *
 * 本接口主要用于：
 * 1. 用户注册
 * 2. 用户登录查询
 * 3. 查询用户信息
 * 4. 删除用户信息
 *
 * 本接口只负责定义方法，
 * 不负责具体实现。
 */
public interface UserDao {

    /**
     * 向数据库中添加用户
     *
     * @param user 用户对象
     */
    void insert(User user);

    /**
     * 根据用户名查询用户
     *
     * 主要用于：
     * 用户登录验证
     *
     * @param username 用户名
     * @return 用户对象
     */
    User selectByUsername(String username);

    /**
     * 查询所有用户
     *
     * @return 用户集合
     */
    List<User> selectAll();

    /**
     * 根据用户ID删除用户
     *
     * @param id 用户ID
     */
    void delete(int id);

}