package org.example.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

/**
 * JDBC 数据库连接工具类。
 *
 * 管理 SQLite 数据库连接的创建、资源释放和数据库初始化。
 * 数据库路径可通过系统属性 {@code studyflow.db.path} 配置，
 * 未配置时默认使用当前工作目录下的 {@code studyflow.db}。
 */
public final class DBUtil {

    private static final String DEFAULT_URL = "jdbc:sqlite:studyflow.db";
    private static final String URL_PROPERTY = "studyflow.db.path";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("Failed to load SQLite JDBC driver: " + e.getMessage());
        }
    }

    private DBUtil() {
        // 工具类，禁止实例化
    }

    /**
     * 获取数据库连接 URL。
     * 优先读取系统属性 {@code studyflow.db.path}，未设置则返回默认值。
     */
    public static String getUrl() {
        String path = System.getProperty(URL_PROPERTY);
        return (path != null && !path.isBlank()) ? "jdbc:sqlite:" + path : DEFAULT_URL;
    }

    /**
     * 获取数据库连接，自动启用外键约束。
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(getUrl());
        try (Statement stmt = conn.createStatement()) {
            // 启用 SQLite 外键约束
            stmt.execute("PRAGMA foreign_keys = ON");
        }
        return conn;
    }

    /**
     * 安全关闭 Connection。
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ignored) {
                // 关闭时忽略异常
            }
        }
    }

    /**
     * 安全关闭 Statement。
     */
    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ignored) {
            }
        }
    }

    /**
     * 安全关闭 ResultSet。
     */
    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ignored) {
            }
        }
    }

    /**
     * 安全关闭所有数据库资源（Connection + Statement + ResultSet）。
     */
    public static void closeAll(Connection conn, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(conn);
    }

    /**
     * 初始化数据库：执行 schema.sql 中的建表语句。
     * 此方法幂等（CREATE TABLE IF NOT EXISTS），可在应用启动时安全调用。
     */
    public static void initDatabase() {
        String sql = readSchemaSql();
        if (sql == null || sql.isBlank()) {
            throw new RuntimeException("schema.sql not found or empty in classpath");
        }

        // 按分号分割多条 SQL 语句，逐条执行
        String[] statements = sql.split(";");
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            for (String statement : statements) {
                String trimmed = statement.trim();
                if (!trimmed.isEmpty()) {
                    stmt.execute(trimmed);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    /**
     * 从 classpath 读取 schema.sql 文件内容。
     */
    private static String readSchemaSql() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(
                                DBUtil.class.getClassLoader().getResourceAsStream("schema.sql")),
                        StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read schema.sql", e);
        }
    }
}
