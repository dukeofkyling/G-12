package com.challenge.client;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Register {
    public static void registerParticipant(Scanner scanner, PrintWriter writer) {
        System.out.println("Enter the following details separated by spaces:");
        System.out.println(
                "username first_name last_name email date_of_birth(YYYY-MM-DD) school_registration_number image_file_path password");

        // Read the entire line of input
        String input = scanner.nextLine();
        String[] data = input.split(" ");

        // Ensure the correct number of fields are provided
        if (data.length != 8) {
            System.out.println("Invalid input. Please enter all required fields separated by spaces.");
            return;
        }

        String username = data[0];
        String firstName = data[1];
        String lastName = data[2];
        String email = data[3];
        String dob = data[4];
        String schoolRegNum = data[5];
        String imageFilePath = data[6];
        String password = data[7];

        // Send this data to the server
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mathematics_challenge", "root",
                "")) {
            // Check if the provided school registration number exists
            String schoolIdQuery = "SELECT id FROM Schools WHERE registration_number = ?";
            PreparedStatement schoolIdStmt = conn.prepareStatement(schoolIdQuery);
            schoolIdStmt.setString(1, schoolRegNum);
            ResultSet rs = schoolIdStmt.executeQuery();

            if (rs.next()) {
                int schoolId = rs.getInt("id");

                // Insert the new participant's data into the Participants table
                String query = "INSERT INTO Participants (username, first_name, last_name, email, date_of_birth, school_id, image_path, password, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'Pending')";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, firstName);
                stmt.setString(3, lastName);
                stmt.setString(4, email);
                stmt.setDate(5, Date.valueOf(dob));
                stmt.setInt(6, schoolId);
                stmt.setString(7, imageFilePath);
                stmt.setString(8, password);
                stmt.executeUpdate();

                writer.println("REGISTER:" + username + "," + firstName + "," + lastName + "," + email + "," + dob + ","
                        + schoolRegNum + "," + imageFilePath + "," + password);
                System.out.println("Registration successful, pending confirmation.");
            } else {
                System.out.println("Invalid school registration number.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
