
package org.example.ui;

import org.example.model.User;

import java.util.Scanner;

public class OtherRoleMenu {

    private final Scanner scanner;
    private final User user;

    public OtherRoleMenu(Scanner scanner, User user) {
        this.scanner = scanner;
        this.user = user;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=== STUDENT MENU ===");
            System.out.println("Welcome, " + user.username() + "!");
            System.out.println("Role: " + user.role());
            System.out.println("1. View account information");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    System.out.println("\n--- ACCOUNT INFORMATION ---");
                    System.out.println("User ID: " + user.id());
                    System.out.println("Username: " + user.username());
                    System.out.println("Role: " + user.role());
                    System.out.println("Status: " + user.status());
                }
                case "0" -> {
                    System.out.println("Logged out.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
