# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run

```bash
# Compile (Maven is bundled with IntelliJ IDEA at /e/IntelliJ IDEA 2025.3.3/plugins/maven/lib/maven3/bin/mvn)
export PATH="/e/IntelliJ IDEA 2025.3.3/plugins/maven/lib/maven3/bin:$PATH"
export JAVA_HOME="/e/Java"

mvn clean compile

# Run tests
mvn test

# Run a single test class
mvn test -Dtest=DBUtilTest

# Run the application
mvn exec:java -Dexec.mainClass="org.example.Main"
```

## Project Overview

StudyFlow is a Java Swing desktop application ("学习打卡系统" / Study Check-in System) for tracking study tasks and daily check-ins.

- **Java 21** (Maven source/target 21), SQLite for persistence (via `org.xerial:sqlite-jdbc`)
- The application currently runs with in-memory data that resets on restart (UI not yet wired to DB)

## Architecture

## Architecture

Layered architecture: **Model → DAO → Service → UI**

### Model (`org.example.model`)
- `User` — id, username, password
- `Task` — id, title, subject, duration, completed
- `CheckInRecord` — id, date, taskName, studyTime

### DAO (`org.example.dao`)
- Interfaces: `UserDao`, `TaskDao` — define CRUD operations
- Implementations: `UserDaoImpl`, `TaskDaoImpl` — **currently empty stubs**

### Service (`org.example.service`)
- Interfaces: `UserService`, `TaskService`, `CheckInService`
- Implementations: `UserServiceImpl`, `TaskServiceImpl`, `CheckInServiceImpl` — **currently empty stubs**

### UI (`org.example.ui`)
- `MainFrame` — main window with dark sidebar (BoxLayout) + CardLayout content area. Four pages: home, task, checkin, statistics
- `LoginFrame` — login/register window, currently a stub
- `HomePanel` — welcome screen with summary info cards
- `TaskPanel` — JTable-based task list with right-click context menu (edit/delete) and add dialog. Uses `DefaultTableModel` directly (no service layer wired yet)
- `CheckInPanel` — daily check-in button
- `StatisticsPanel` — stat cards + chart placeholder
- `AddTaskDialog` — add/edit task modal with form validation

### Util (`org.example.util`)
- `DBUtil` — **implemented**: JDBC connection management, `PRAGMA foreign_keys = ON`, `initDatabase()` auto-creates tables from schema.sql. DB path configurable via `studyflow.db.path` system property.
- `FileUtil` — file-based data persistence (stub)
- `DateUtil` — date formatting/parsing (stub)
- `SwingUtil` — Swing component styling helpers (stub)

### Database
- SQLite via `org.xerial:sqlite-jdbc:3.45.3.0`
- Schema: [`src/main/resources/schema.sql`](src/main/resources/schema.sql) — tables `users`, `tasks` (FK → users), `check_in_records` (FK → users)
- Test database isolated via `studyflow.db.path` system property

## Current State

- UI layer is partially functional (MainFrame, panels, dialog work with in-memory data)
- **Phase 1 complete**: DBUtil, schema.sql, SQLite dependency, JUnit tests all passing
- **DAO implementations and Service implementations are empty stubs** — need implementation
- `LoginFrame` is a stub (no auth flow implemented)
