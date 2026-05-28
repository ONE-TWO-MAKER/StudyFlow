package org.example;

import org.example.dao.CheckInDao;
import org.example.dao.TaskDao;
import org.example.dao.UserDao;
import org.example.dao.impl.CheckInDaoImpl;
import org.example.dao.impl.TaskDaoImpl;
import org.example.dao.impl.UserDaoImpl;
import org.example.model.CheckInRecord;
import org.example.model.Task;
import org.example.model.User;
import org.example.service.CheckInService;
import org.example.service.TaskService;
import org.example.service.UserService;
import org.example.service.impl.CheckInServiceImpl;
import org.example.service.impl.TaskServiceImpl;
import org.example.service.impl.UserServiceImpl;
import org.example.util.DBUtil;
import org.example.util.DateUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 全链路集成测试：验证 DAO + Service 层在真实 SQLite 数据库上的完整业务流。
 */
class IntegrationTest {

    private static final String TEST_DB = "integration_test.db";

    private static UserService userService;
    private static TaskService taskService;
    private static CheckInService checkInService;

    @BeforeAll
    static void setup() {
        System.setProperty("studyflow.db.path", TEST_DB);
        DBUtil.initDatabase();

        UserDao userDao = new UserDaoImpl();
        TaskDao taskDao = new TaskDaoImpl();
        CheckInDao checkInDao = new CheckInDaoImpl();

        userService = new UserServiceImpl(userDao);
        taskService = new TaskServiceImpl(taskDao);
        checkInService = new CheckInServiceImpl(checkInDao);
    }

    @AfterAll
    static void cleanup() {
        File dbFile = new File(TEST_DB);
        if (dbFile.exists()) {
            dbFile.delete();
        }
    }

    @Test
    void fullBusinessFlow_shouldSucceed() {
        // ==========================================================
        // 环节 A：注册登录
        // ==========================================================
        User regUser = new User();
        regUser.setUsername("testuser");
        regUser.setPassword("pass123");
        userService.register(regUser);

        // 错误密码应抛出异常
        assertThrows(RuntimeException.class, () ->
                userService.login("testuser", "wrongpass"),
                "密码错误时应抛出异常"
        );

        // 正确密码登录，拿到 userId
        User loggedIn = userService.login("testuser", "pass123");
        assertNotNull(loggedIn);
        assertTrue(loggedIn.getId() > 0, "登录返回的 userId 应大于 0");
        int userId = loggedIn.getId();

        // ==========================================================
        // 环节 B：任务管理
        // ==========================================================
        Task task1 = new Task();
        task1.setUserId(userId);
        task1.setTitle("Java 核心技术");
        task1.setSubject("Java");
        task1.setDuration(120);
        taskService.addTask(task1);

        Task task2 = new Task();
        task2.setUserId(userId);
        task2.setTitle("数据结构");
        task2.setSubject("计算机科学");
        task2.setDuration(90);
        taskService.addTask(task2);

        assertEquals(2, taskService.getAllTasks(userId).size(),
                "该用户应有 2 个任务");

        // ==========================================================
        // 环节 C：打卡操作
        // ==========================================================
        CheckInRecord record = new CheckInRecord();
        record.setUserId(userId);
        record.setDate(DateUtil.getTodayDate());
        record.setTaskName("Java 核心技术");
        record.setStudyTime(60);
        checkInService.addRecord(record);

        // ==========================================================
        // 环节 D：数据统计
        // ==========================================================
        assertEquals(1, checkInService.getAllRecords(userId).size(),
                "该用户应有 1 条打卡记录");

        CheckInRecord saved = checkInService.getAllRecords(userId).get(0);
        assertEquals("Java 核心技术", saved.getTaskName());
        assertEquals(60, saved.getStudyTime());
        assertEquals(userId, saved.getUserId());
        assertEquals(DateUtil.getTodayDate(), saved.getDate());

        assertEquals(60, checkInService.getTotalStudyTime(userId),
                "总学习时长应为 60 分钟");
    }
}
