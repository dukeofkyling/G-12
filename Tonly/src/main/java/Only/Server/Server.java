package Only.Server;




import javax.mail.*;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Server {
    private static final Map<String, User> users = new HashMap<>(); // Store user info
    private static final Map<String, Boolean> loggedInUsers = new HashMap<>(); // Track logged-in users

    public static void main(String[] args) {
        int port = 8080;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Handle client request in a separate thread
                new Thread(() -> handleClientRequest(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // private static void handleClientRequest(Socket clientSocket) {
    //     try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    //          PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

    //         String clientRequest;
    //         while ((clientRequest = in.readLine()) != null) {
    //             System.out.println("Received from client: " + clientRequest);

    //             // Process client request (menu options)
    //             String response = processClientRequest(clientRequest);

    //             // Send response back to client
    //             out.println(response);
    //         }

    //         System.out.println("Client disconnected: " + clientSocket.getInetAddress());
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }
    
    private static void handleClientRequest(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
    
            String clientRequest;
            while ((clientRequest = in.readLine()) != null) {
                System.out.println("Received from client: " + clientRequest);
    
                // Process client request (menu options)
                String response = processClientRequest(clientRequest);
    
                // Send response back to client
                out.println(response);
            }
    
            System.out.println("Client disconnected: " + clientSocket.getInetAddress());
        } catch (SocketException e) {
            System.err.println("Client connection reset: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    private static String processClientRequest(String request) {
        String[] parts = request.split(":", 2);
        String command = parts[0];
        String data = parts.length > 1 ? parts[1] : "";

        switch (command.toLowerCase()) {
            case "register":
                return handleRegister(data);
            case "login":
                return handleLogin(data);
            case "viewchallenge":
                return handleViewChallenge(data);
            case "attempt":
                return handleAttemptChallenge(data);
            case "viewparticipant":
                return handleViewParticipant(data);
            default:
                return "Invalid option. Please try again.";
        }
    }

    private static String handleRegister(String data) {
        String[] fields = data.split(";");
        if (fields.length != 7) {
            return "Invalid registration data.";
        }

        String username = fields[0];
        if (users.containsKey(username)) {
            return "Username already exists.";
        }

        User newUser = new User(username, fields[1], fields[2], fields[3], fields[4], fields[5], fields[6]);
        users.put(username, newUser);



        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tonly", "root", "")) {
            String sql = "INSERT INTO users (username, PASSWORD, firstName, lastName,emailAddress, dateOfBirth,  schoolRegistrationNumber) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            // Create a PreparedStatement with auto-generated keys
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
          

            pstmt.setString(1, newUser.username);
            pstmt.setString(2, newUser.password);
            pstmt.setString(3, newUser.firstName);
            pstmt.setString(4, newUser.lastName);
            pstmt.setString(5, newUser.email);
            pstmt.setString(6, newUser.dob);
            pstmt.setString(7, newUser.schoolRegNumber);

            
            // Execute the INSERT statement
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Data inserted successfully.");
                
                // Retrieve the auto-generated keys if any
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    System.out.println("Generated ID: " + generatedKeys.getInt(1));
                }
            } else {
                System.out.println("Failed to insert data.");
            }
            
            // Close resources
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    



       System.out.println("Registered new user: " + username);

        // Send confirmation email
      // EmailSender.sendConfirmationEmail(newUser.getEmail(), "Your Account Confirmation", "Thank you for registering! Your account has been confirmed.");

        return "Registration successful.";
}


    private static String handleLogin(String data) {
        String[] fields = data.split(";");
        if (fields.length != 2) {
            return "Invalid login data.";
        }

        String username = fields[0];
        String password = fields[1];
        User user = users.get(username);

        if (user != null && user.getPassword().equals(password)) {
            loggedInUsers.put(username, true);
            System.out.println("User logged in: " + username);
            return "Login successful.";
        } else {
            return "Login failed. Please check your username and password.";
        }
    }

    private static String handleViewChallenge(String username) {
        if (isLoggedIn(username)) {
            return "Challenges: Challenge1, Challenge2, Challenge3";
        } else {
            return "Please login to view challenges.";
        }
    }

    private static String handleAttemptChallenge(String data) {
        String[] fields = data.split(";");
        if (fields.length != 2) {
            return "Invalid attempt data.";
        }

        String username = fields[0];
        if (isLoggedIn(username)) {
            String challengeId = fields[1];
            return "Challenge " + challengeId + " attempted successfully.";
        } else {
            return "Please login to attempt challenges.";
        }
    }

    private static String handleViewParticipant(String username) {
        if (isLoggedIn(username)) {
            return "Participants: User1, User2, User3";
        } else {
            return "Please login to view participants.";
        }
    }

    private static boolean isLoggedIn(String username) {
        return loggedInUsers.getOrDefault(username, false);
    }

    // public static class EmailSender {
    //     public static void sendEmail(String to, String subject, String text) {
    //         final String from = "suzieklein003@gmail.com";
    //         final String password = "nabasumba";
    //         String host = "smtp.gmail.com";
    //         String port = "587";

    //         Properties props = new Properties();
    //         props.put("mail.smtp.host", "smtp.gmail.com");
    //         props.put("mail.smtp.port", "587");
    //         props.put("mail.smtp.starttls.enable", "true");
    //         props.put("mail.smtp.auth", "true");
    //         props.put("mail.smtp.host", host);
    //         props.put("mail.smtp.port", port);

    //         Session session = Session.getInstance(props,
    //                 new javax.mail.Authenticator() {
    //                     protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
    //                         return new PasswordAuthentication(from, password);
    //                     }
    //                 });

    //         try {
    //             MimeMessage message = new MimeMessage(session);
    //             message.setFrom(new InternetAddress(from));
    //             message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
    //             message.setSubject(subject);
    //             message.setText(text);
    //             Transport.send(message);
    //             System.out.println("Sent message successfully....");
    //         } catch (MessagingException mex) {
    //             mex.printStackTrace();
    //         }
    //     }
   

public class EmailSender {
    
    public  void  sendEmail() throws MessagingException {
        String host = "smtp.gmail.com";
        String port = "587";
        String username = "suzieklein003@gmail.com";
        String password = "nabasumba";
        
        // Set properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        
        // Create session
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
        // Create message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("recipient@example.com"));
        message.setSubject("Test Email");
        message.setText("This is a test email.");
        
        // Send message
        Transport.send(message);
        System.out.println("Email sent successfully.");
    
        try {
            sendEmail();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}


        // Method to send a confirmation email
        public static void sendConfirmationEmail(String to, String subject, String body) {
            // Predefined content for a confirmation email
            String confirmationText = "Thank you for registering! Your account has been confirmed.\n\n"
                    + "Best regards,\nThe Team";

            // Call the existing sendEmail method with the predefined content
            sendConfirmationEmail(to, subject, body + "\n\n" + confirmationText);
        }
    }


class User {
    public String username;
    public String password;
    public String firstName;
    public String lastName;
    public String email;
    public String dob;
    public String schoolRegNumber; // New field

    public User(String username, String password, String firstName, String lastName, String email, String dob, String schoolRegNumber) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
        this.schoolRegNumber = schoolRegNumber; // Initialize new field
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }

    public String getSchoolRegNumber() {
        return schoolRegNumber;
    }
}