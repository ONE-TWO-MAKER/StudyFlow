package org.example.util;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DBUtilTest {

    private static final String TEST_DB = "studyflow-test.db";

    @BeforeAll
    static void setup() {
        // 使用测试专用数据库文件，不污染开发数据
        System.setProperty("studyflow.db.path", TEST_DB);
    }

    @AfterAll
    static void cleanup() {
        File dbFile = new File(TEST_DB);
        if (dbFile.exists()) {
            dbFile.delete();
        }
    }

    @Test
    void getConnection_shouldReturnValidConnection() throws Exception {
        try (Connection conn = DBUtil.getConnection()) {
            assertNotNull(conn, "Connection should not be null");
            assertFalse(conn.isClosed(), "Connection should be open");
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT 1")) {
                assertTrue(rs.next(), "Should be able to execute query");
                assertEquals(1, rs.getInt(1));
            }
        }
    }

    @Test
    void initDatabase_shouldCreateAllTables() {
        // When
        DBUtil.initDatabase();

        // Then — query sqlite_master to verify all 3 tables exist
        Set<String> expectedTables = new HashSet<>();
        expectedTables.add("users");
        expectedTables.add("tasks");
        expectedTables.add("check_in_records");

        Set<String> actualTables = new HashSet<>();
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT name FROM sqlite_master WHERE type='table' AND name NOT LIKE 'sqlite_%'")) {
            while (rs.next()) {
                actualTables.add(rs.getString("name"));
            }
        } catch (Exception e) {
            fail("Failed to query sqlite_master: " + e.getMessage());
        }

        assertEquals(expectedTables, actualTables,
                "All three tables should exist after initDatabase()");
    }

    @Test
    void initDatabase_shouldBeIdempotent() {
        // Given — already initialized by previous test
        // When — call initDatabase again
        DBUtil.initDatabase();

        // Then — still exactly 3 tables (no duplicates)
        int tableCount = 0;
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT COUNT(*) FROM sqlite_master WHERE type='table' AND name NOT LIKE 'sqlite_%'")) {
            if (rs.next()) {
                tableCount = rs.getInt(1);
            }
        } catch (Exception e) {
            fail("Failed to count tables: " + e.getMessage());
        }

        assertEquals(3, tableCount, "initDatabase() should be idempotent");
    }

    @Test
    void closeAll_shouldHandleNullSafely() {
        // Should not throw with any combination of nulls
        assertDoesNotThrow(() -> DBUtil.closeAll(null, null, null));
        assertDoesNotThrow(() -> DBUtil.closeAll(null, null, null));
    }

    @Test
    void getUrl_shouldReturnTestUrl() {
        String url = DBUtil.getUrl();
        assertTrue(url.contains(TEST_DB), "URL should contain test database path");
    }
}
