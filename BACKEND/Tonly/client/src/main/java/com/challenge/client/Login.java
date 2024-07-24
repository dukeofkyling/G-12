package com.challenge.client;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Login {
    public static String loginParticipant(Scanner scanner, PrintWriter writer) {
        System.out.println("Enter your username and password separated by a space:");
        
        String input = scanner.nextLine();
        String[] data = input.split(" ");

        // Ensure the correct number of fields are provided
        if (data.length != 2) {
            System.out.println("Invalid input. Please enter your username and password separated by a space.");
            return null;
        }

        String username = data[0];
        String password = data[1];

        // Verify the username and password
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mathematics_challenge", "root", "")) {
            String query = "SELECT * FROM Participants WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                writer.println("LOGIN_SUCCESS:" + username);
                System.out.println("Login successful. Welcome, " + username + "!");
                return username;
            } else {
                System.out.println("Invalid username or password.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String loginRepresentative(Scanner scanner, PrintWriter writer) {
        System.out.println("Enter your name:");
        
        String name = scanner.nextLine();

        // Verify the name
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mathematics_challenge", "root", "")) {
            String query = "SELECT * FROM Representatives WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                writer.println("REPRESENTATIVE_LOGIN_SUCCESS:" + name);
                System.out.println("Login successful. Welcome, " + name + "!");
                return name;
            } else {
                System.out.println("Invalid name.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
