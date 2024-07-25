
package Only;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.*;
import javax.mail.internet.*;

class Applicant {
    String name;
    boolean isConfirmed;

    public Applicant(String name, boolean isConfirmed) {
        this.name = name;
        this.isConfirmed = isConfirmed;
    }

    @Override
    public String toString() {
        return name + (isConfirmed ? " - Confirmed" : " - Not Confirmed");
    }
}

public class ViewApplicants {

    private static final List<Applicant> applicants = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    // JDBC connection parameters
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/tonly";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "";

    // Email settings
    private static final String EMAIL_HOST = "smtp.gmail.com"; // Change to your SMTP server
    private static final String EMAIL_PORT = "587"; // Common port for SMTP
    private static final String EMAIL_USERNAME = "suzieklein003@gmail.com"; // Your email
    private static final String EMAIL_PASSWORD = "bujn mzlc nxke iucgs"; // Your email password

    public static void main(String[] args) {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            while (true) {
                System.out.println("\nSchool Challenge Application Management");
                System.out.println("1. View All Applicants");
                System.out.println("2. Update Applicant Status");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over

                switch (choice) {
                    case 1:
                        viewAllApplicants();
                        break;
                    case 2:
                        updateApplicantStatus();
                        break;
                    case 3:
                        System.exit(0);
                    default:
                        System.out.println("Invalid option. Please choose again.");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
    }

    private static void viewAllApplicants() throws SQLException {
        System.out.println("Current Applicants:");
        applicants.clear(); // Clear the list before retrieving new data

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT username, is_confirmed FROM users"); // Adjust if needed
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                boolean isConfirmed = resultSet.getBoolean("is_confirmed");
                applicants.add(new Applicant(username, isConfirmed));
            }
        }

        for (Applicant applicant : applicants) {
            System.out.println(applicant);
        }
    }

    private static void updateApplicantStatus() throws SQLException {
        System.out.print("Enter applicant's name to update status: ");
        String username = scanner.nextLine();

        try (Connection connection = getConnection()) {
            String query = "UPDATE users SET is_confirmed = ? WHERE username = ?"; // Adjust if needed
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                System.out.println("Found applicant: " + username);
                System.out.println("1. Confirm");
                System.out.println("2. Reject");
                int action = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over

                boolean confirmStatus = (action == 1);
                statement.setBoolean(1, confirmStatus);
                statement.setString(2, username);
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Applicant status updated successfully.");
                    sendEmailNotification(username, confirmStatus);
                } else {
                    System.out.println("No applicant found with name: " + username);
                }
            }
        }
    }

    private static void sendEmailNotification(String username, boolean isConfirmed) {
        // Email settings
        Properties props = new Properties();
        props.put("mail.smtp.host", EMAIL_HOST);
        props.put("mail.smtp.port", EMAIL_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(username));
            message.setSubject("Application Status Update");
            message.setText("Dear Applicant,\n\nYour application status has been updated to: "
                            + (isConfirmed ? "Confirmed" : "Rejected") + ".\n\nBest regards,\nSchool Challenge");

            Transport.send(message);
            System.out.println("Email sent to " + username);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

