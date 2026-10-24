
package org.example.ui;

import org.example.dao.UserDao;
import org.example.model.User;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class Authentication {

    private final UserDao userDao = new UserDao();
    private final Scanner scanner;

    public Authentication(Scanner scanner) {
        this.scanner = scanner;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=== STUDENT CONSOLE APP ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> register();
                case "2" -> login();
                case "0" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (username.isBlank() || password.isBlank()) {
            System.out.println("All fields are required.");
            return;
        }

        try {
            if (userDao.findByUsername(username).isPresent()) {
                System.out.println("Username already exists.");
                return;
            }

            if (userDao.register(username, password)) {
                System.out.println(
                        "Registration successful! Await administrator approval."
                );
            }

        } catch (SQLException e) {
            System.out.println("Registration error: " + e.getMessage());
        }
    }

    private void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            Optional<User> result = userDao.findByUsername(username);

            if (result.isEmpty()
                    || !result.get().password().equals(password)) {
                System.out.println("Invalid username or password.");
                return;
            }

            User user = result.get();

            if (!user.status().equals("APPROVED")) {
                System.out.println(
                        "Your account is pending administrator approval."
                );
                return;
            }

            if (user.role().equals("ADMIN")) {
                new AdminMenu(scanner, userDao).showMenu();
            } else {
                new OtherRoleMenu(scanner, user).showMenu();
            }

        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }
    }
}
