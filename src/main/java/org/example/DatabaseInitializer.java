
package org.example.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() throws SQLException {

        String usersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL,
                role TEXT NOT NULL DEFAULT 'STUDENT',
                status TEXT NOT NULL DEFAULT 'PENDING'
            )
            """;

        String studentsTable = """
            CREATE TABLE IF NOT EXISTS students (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                student_id TEXT NOT NULL UNIQUE,
                first_name TEXT NOT NULL,
                last_name TEXT NOT NULL,
                course TEXT NOT NULL,
                year_level INTEGER NOT NULL
            )
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(usersTable);
            statement.execute(studentsTable);

            statement.executeUpdate("""
                INSERT OR IGNORE INTO users
                    (username, password, role, status)
                VALUES
                    ('admin', 'admin123', 'ADMIN', 'APPROVED')
                """);

            System.out.println("Database initialized successfully.");
        }
    }
}
