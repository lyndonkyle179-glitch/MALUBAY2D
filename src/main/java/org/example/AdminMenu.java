
package org.example.ui;

import org.example.dao.UserDao;
import org.example.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    private final Scanner scanner;
    private final UserDao userDao;

    public AdminMenu(Scanner scanner, UserDao userDao) {
        this.scanner = scanner;
        this.userDao = userDao;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. View pending users");
            System.out.println("2. Approve user");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> viewPendingUsers();
                case "2" -> approveUser();
                case "0" -> {
                    System.out.println("Admin logged out.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void viewPendingUsers() {
        try {
            List<User> users = userDao.getPendingUsers();

            if (users.isEmpty()) {
                System.out.println("No pending users.");
                return;
            }

            System.out.println("\n--- PENDING USERS ---");

            for (User user : users) {
                System.out.println(
                        "ID: " + user.id()
                                + " | Username: " + user.username()
                                + " | Status: " + user.status()
                );
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void approveUser() {
        viewPendingUsers();

        System.out.print("Enter user ID to approve: ");
        String input = scanner.nextLine().trim();

        try {
            int id = Integer.parseInt(input);

            if (userDao.approveUser(id)) {
                System.out.println("User approved successfully.");
            } else {
                System.out.println(
                        "User not found or already approved."
                );
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid numeric ID.");
        } catch (SQLException e) {
            System.out.println("Approval error: " + e.getMessage());
        }
    }
}
