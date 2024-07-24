package com.challenge.client;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewApplicants {
    public static void viewApplicants(PrintWriter writer) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mathematics_challenge", "root",
                "");
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Participants WHERE status = 'Pending'")) {

            ResultSet rs = stmt.executeQuery();
            System.out.println("List of pending applicants:");
            writer.println("List of pending applicants:");

            while (rs.next()) {
                String username = rs.getString("username");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                System.out.println(username + ": " + firstName + " " + lastName + " (" + email + ")");
                writer.println(username + ": " + firstName + " " + lastName + " (" + email + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
