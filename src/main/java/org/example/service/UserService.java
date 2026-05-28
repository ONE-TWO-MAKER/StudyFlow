package org.example.service;

import org.example.model.User;

/**
 * 用户服务接口，定义用户相关的业务操作规范。
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录成功的用户对象（包含 userId）
     * @throws RuntimeException 登录失败时抛出含中文错误信息
     */
    User login(String username, String password);

    /**
     * 用户注册
     *
     * @param user 用户对象
     * @throws RuntimeException 注册失败时抛出含中文错误信息
     */
    void register(User user);
}
