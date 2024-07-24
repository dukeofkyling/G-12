package com.challenge.client;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ConfirmApplicant {
    public static void confirmApplicant(Scanner scanner, PrintWriter writer) {
        System.out.println("Enter username of applicant to confirm:");
        String username = scanner.nextLine();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mathematics_challenge", "root",
                "")) {
            // Check if the participant exists and is pending
            String selectQuery = "SELECT * FROM Participants WHERE username = ? AND status = 'Pending'";
            PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
            selectStmt.setString(1, username);
            ResultSet resultSet = selectStmt.executeQuery();

            if (!resultSet.next()) {
                System.out.println("No pending applicant with the given username.");
                return;
            }

            // Assuming you have participant details in resultSet, you can print them if
            // needed.

            // Confirm applicant logic
            System.out.println("Confirm applicant? (yes/no)");
            String confirmation = scanner.nextLine();

            if (confirmation.equalsIgnoreCase("yes")) {
                // Update status to confirmed
                String updateQuery = "UPDATE Participants SET status = 'Confirmed' WHERE username = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setString(1, username);
                int rowsAffected = updateStmt.executeUpdate();

                if (rowsAffected > 0) {
                    writer.println("CONFIRM_APPLICANT:" + username);
                    System.out.println("Applicant confirmed.");
                } else {
                    System.out.println("Failed to confirm applicant.");
                }
            } else {
                // Reject applicant
                String insertQuery = "INSERT INTO Rejected_Participants (participant_id, details) VALUES (?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setInt(1, resultSet.getInt("participant_id")); // Assuming participant_id exists
                insertStmt.setString(2, "Details of rejection"); // Replace with actual details if needed
                insertStmt.executeUpdate();

                // Delete from Participants table
                String deleteQuery = "DELETE FROM Participants WHERE username = ?";
                PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
                deleteStmt.setString(1, username);
                int rowsDeleted = deleteStmt.executeUpdate();

                if (rowsDeleted > 0) {
                    System.out.println("Applicant rejected.");
                } else {
                    System.out.println("Failed to reject applicant.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
