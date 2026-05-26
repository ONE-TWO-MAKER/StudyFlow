package org.example.service;
import model.User;
/**
 * 用户服务接口，定义用户相关的业务操作规范。
 */
public interface UserService {
    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 是否登录成功
     */
    boolean login(String username, String password);

    /**
     * 用户注册
     *
     * @param user 用户对象
     */
    void register(User user);

}
