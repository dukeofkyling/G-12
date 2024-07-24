package com.challenge.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the server");

            boolean running = true;
            boolean loggedIn = false;
            boolean isRepresentative = false;
            String loggedInUsername = null;

            while (running) {
                if (!loggedIn) {
                    System.out.println(
                            "Register(username first_name last_name email date_of_birth(YYYY-MM-DD) school_registration_number image_file_path password)");
                    System.out.println("Login as Participant");
                    System.out.println("Login as Representative");
                    System.out.println("Exit");
                    System.out.println();

                    String choice = scanner.nextLine().trim().toLowerCase();

                    switch (choice) {
                        case "register":
                            // Call registerParticipant method from Register class
                            Register.registerParticipant(scanner, writer);
                            break;

                        case "login as participant":
                            // Call loginParticipant method from Login class
                            loggedInUsername = Login.loginParticipant(scanner, writer);
                            loggedIn = loggedInUsername != null;
                            break;

                        case "login as representative":
                            // Call loginRepresentative method from Login class
                            loggedInUsername = Login.loginRepresentative(scanner, writer);
                            loggedIn = loggedInUsername != null;
                            isRepresentative = loggedIn;
                            break;

                        case "exit":
                            // Exit the application
                            System.out.println("Exiting...");
                            running = false;
                            break;

                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } else {
                    if (isRepresentative) {
                        System.out.println("Welcome to the Mathematics Challenge System (Representative)");
                        System.out.println("View Applicants");
                        System.out.println("Confirm Applicant");
                        System.out.println("Logout");
                        System.out.println("Exit");
                        System.out.println();

                        String choice = scanner.nextLine().trim().toLowerCase();

                        switch (choice) {
                            case "view applicants":
                                // Implement viewApplicants method
                                ViewApplicants.viewApplicants(writer);
                                break;

                            case "confirm applicant":
                                // Call confirmApplicant method from ConfirmApplicant class
                                ConfirmApplicant.confirmApplicant(scanner, writer);
                                break;

                            case "logout":
                                // Logout
                                loggedIn = false;
                                isRepresentative = false;
                                loggedInUsername = null;
                                System.out.println("Logged out.");
                                break;

                            case "exit":
                                // Exit the application
                                System.out.println("Exiting...");
                                running = false;
                                break;

                            default:
                                System.out.println("Invalid choice. Please try again.");
                        }
                    } else {
                        System.out.println("Welcome to the Mathematics Challenge System");
                        System.out.println("View Challenges");
                        System.out.println("Attempt Challenge");
                        System.out.println("Logout");
                        System.out.println("Exit");
                        System.out.println();

                        String choice = scanner.nextLine().trim().toLowerCase();

                        switch (choice) {
                            case "view challenges":
                                // Call viewChallenges method from ViewChallenges class
                                ViewChallenges.viewChallenges(writer);
                                break;

                            case "attempt challenge":
                                // Call attemptChallenge method from AttemptChallenge class
                                AttemptChallenge.attemptChallenge(scanner, writer);
                                break;

                            case "logout":
                                // Logout
                                loggedIn = false;
                                loggedInUsername = null;
                                System.out.println("Logged out.");
                                break;

                            case "exit":
                                // Exit the application
                                System.out.println("Exiting...");
                                running = false;
                                break;

                            default:
                                System.out.println("Invalid choice. Please try again.");
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String response;
            while ((response = reader.readLine()) != null) {
                if (response.equals("END_OF_CHALLENGES")) {
                    System.out.println("End of challenges.");
                    break;
                } else {
                    System.out.println("Server response: " + response);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
