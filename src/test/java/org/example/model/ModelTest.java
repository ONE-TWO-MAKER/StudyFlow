package org.example.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void user_shouldSupportDefaultConstructor() {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    void user_shouldSupportParameterizedConstructor() {
        User user = new User(1, "alice", "secret123");
        assertEquals(1, user.getId());
        assertEquals("alice", user.getUsername());
        assertEquals("secret123", user.getPassword());
    }

    @Test
    void user_gettersAndSetters_shouldWork() {
        User user = new User();
        user.setId(2);
        user.setUsername("bob");
        user.setPassword("p@ss");

        assertEquals(2, user.getId());
        assertEquals("bob", user.getUsername());
        assertEquals("p@ss", user.getPassword());
    }

    @Test
    void user_toString_shouldNotContainPassword() {
        User user = new User(1, "alice", "secret123");
        String str = user.toString();
        assertFalse(str.contains("secret123"), "toString should not leak password");
        assertFalse(str.contains("password"), "toString should not contain password field");
        assertTrue(str.contains("alice"), "toString should contain username");
    }

    @Test
    void user_equals_shouldBeBasedOnId() {
        User user1 = new User(1, "alice", "pass1");
        User user2 = new User(1, "bob", "pass2");
        User user3 = new User(3, "alice", "pass1");

        assertEquals(user1, user2, "Users with same id should be equal");
        assertNotEquals(user1, user3, "Users with different id should not be equal");
    }

    @Test
    void user_hashCode_shouldBeBasedOnId() {
        User user1 = new User(1, "alice", "pass");
        User user2 = new User(1, "bob", "other");
        assertEquals(user1.hashCode(), user2.hashCode(), "HashCode should be based on id");
    }

    @Test
    void user_equals_shouldHandleNullAndDifferentType() {
        User user = new User(1, "alice", "pass");
        assertNotEquals(null, user);
        assertNotEquals("string", user);
    }

    @Test
    void user_equals_shouldBeReflexive() {
        User user = new User(1, "alice", "pass");
        assertEquals(user, user);
    }

    @Test
    void task_shouldSupportDefaultConstructor() {
        Task task = new Task();
        assertNotNull(task);
    }

    @Test
    void task_shouldSupportParameterizedConstructor() {
        Task task = new Task(1, 10, "Math Homework", "Math", 60, true);
        assertEquals(1, task.getId());
        assertEquals(10, task.getUserId());
        assertEquals("Math Homework", task.getTitle());
        assertEquals("Math", task.getSubject());
        assertEquals(60, task.getDuration());
        assertTrue(task.isCompleted());
    }

    @Test
    void task_gettersAndSetters_shouldWork() {
        Task task = new Task();
        task.setId(2);
        task.setUserId(20);
        task.setTitle("Reading");
        task.setSubject("English");
        task.setDuration(30);
        task.setCompleted(false);

        assertEquals(2, task.getId());
        assertEquals(20, task.getUserId());
        assertEquals("Reading", task.getTitle());
        assertEquals("English", task.getSubject());
        assertEquals(30, task.getDuration());
        assertFalse(task.isCompleted());
    }

    @Test
    void task_toString_shouldContainAllFields() {
        Task task = new Task(1, 10, "Math", "Math", 45, false);
        String str = task.toString();
        assertTrue(str.contains("userId=10"));
        assertTrue(str.contains("Math"));
        assertTrue(str.contains("completed=false"));
    }

    @Test
    void checkInRecord_shouldSupportDefaultConstructor() {
        CheckInRecord record = new CheckInRecord();
        assertNotNull(record);
    }

    @Test
    void checkInRecord_shouldSupportParameterizedConstructor() {
        CheckInRecord record = new CheckInRecord(1, 10, "2026-05-27", "Math", 120);
        assertEquals(1, record.getId());
        assertEquals(10, record.getUserId());
        assertEquals("2026-05-27", record.getDate());
        assertEquals("Math", record.getTaskName());
        assertEquals(120, record.getStudyTime());
    }

    @Test
    void checkInRecord_gettersAndSetters_shouldWork() {
        CheckInRecord record = new CheckInRecord();
        record.setId(2);
        record.setUserId(20);
        record.setDate("2026-05-26");
        record.setTaskName("Reading");
        record.setStudyTime(45);

        assertEquals(2, record.getId());
        assertEquals(20, record.getUserId());
        assertEquals("2026-05-26", record.getDate());
        assertEquals("Reading", record.getTaskName());
        assertEquals(45, record.getStudyTime());
    }

    @Test
    void checkInRecord_toString_shouldContainAllFields() {
        CheckInRecord record = new CheckInRecord(1, 10, "2026-05-27", "Math", 120);
        String str = record.toString();
        assertTrue(str.contains("userId=10"));
        assertTrue(str.contains("2026-05-27"));
        assertTrue(str.contains("Math"));
        assertTrue(str.contains("studyTime=120"));
    }
}
