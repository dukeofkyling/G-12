package com.challenge.client;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewChallenges {
    public static void viewChallenges(PrintWriter writer) {
        // Fetch challenges from the server and display them
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mathematics_challenge", "root",
                "")) {
            String query = "SELECT * FROM Challenges";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            System.out.println("List of active challenges:");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + ". " + rs.getString("name") + " (from " + rs.getDate("start_date")
                        + " to " + rs.getDate("end_date") + ")");
            }
            writer.println("VIEW_CHALLENGES");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
