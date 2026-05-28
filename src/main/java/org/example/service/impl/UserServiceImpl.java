package org.example.service.impl;

import org.example.dao.UserDao;
import org.example.model.User;
import org.example.service.UserService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User login(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new RuntimeException("用户名不能为空");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new RuntimeException("密码不能为空");
        }

        User user = userDao.selectByUsername(username.trim());
        if (user == null) {
            throw new RuntimeException("用户名不存在");
        }
        if (!user.getPassword().equals(hashPassword(password))) {
            throw new RuntimeException("密码错误");
        }
        return user;
    }

    @Override
    public void register(User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new RuntimeException("用户名不能为空");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new RuntimeException("密码不能为空");
        }
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("密码长度不能少于6位");
        }

        User existing = userDao.selectByUsername(user.getUsername().trim());
        if (existing != null) {
            throw new RuntimeException("该用户名已被注册");
        }

        user.setUsername(user.getUsername().trim());
        user.setPassword(hashPassword(user.getPassword()));
        userDao.insert(user);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 算法不可用", e);
        }
    }
}
