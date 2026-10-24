
package org.example.dao;

import org.example.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao {

    public boolean register(String username, String password)
            throws SQLException {

        String sql = """
            INSERT INTO users (username, password, role, status)
            VALUES (?, ?, 'STUDENT', 'PENDING')
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            return statement.executeUpdate() == 1;
        }
    }

    public Optional<User> findByUsername(String username)
            throws SQLException {

        String sql = """
            SELECT id, username, password, role, status
            FROM users
            WHERE username = ?
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return Optional.of(mapUser(result));
                }
            }
        }

        return Optional.empty();
    }

    public List<User> getPendingUsers() throws SQLException {

        List<User> users = new ArrayList<>();

        String sql = """
            SELECT id, username, password, role, status
            FROM users
            WHERE status = 'PENDING'
            ORDER BY id
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                users.add(mapUser(result));
            }
        }

        return users;
    }

    public boolean approveUser(int id) throws SQLException {

        String sql = """
            UPDATE users
            SET status = 'APPROVED'
            WHERE id = ? AND status = 'PENDING'
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        }
    }

    private User mapUser(ResultSet result) throws SQLException {
        return new User(
                result.getInt("id"),
                result.getString("username"),
                result.getString("password"),
                result.getString("role"),
                result.getString("status")
        );
    }
}
