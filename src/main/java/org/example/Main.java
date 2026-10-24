
package org.example;

import org.example.dao.DatabaseInitializer;
import org.example.ui.Authentication;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try {
            DatabaseInitializer.initialize();

            try (Scanner scanner = new Scanner(System.in)) {
                Authentication authentication =
                        new Authentication(scanner);

                authentication.showMenu();
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
