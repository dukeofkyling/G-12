package com.challenge.client;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AttemptChallenge {
    public static void attemptChallenge(Scanner scanner, PrintWriter writer) {
        System.out.println("Enter challenge number:");
        int challengeNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Check if the challenge number is valid
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mathematics_challenge", "root",
                "")) {
            String challengeQuery = "SELECT * FROM Challenges WHERE id = ?";
            PreparedStatement challengeStmt = conn.prepareStatement(challengeQuery);
            challengeStmt.setInt(1, challengeNumber);
            ResultSet challengeRs = challengeStmt.executeQuery();
            if (!challengeRs.next()) {
                System.out.println("Invalid challenge number");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mathematics_challenge", "root",
                "")) {
            String questionQuery = "SELECT * FROM Questions WHERE challenge_id = ?";
            PreparedStatement questionStmt = conn.prepareStatement(questionQuery);
            questionStmt.setInt(1, challengeNumber);
            ResultSet questionRs = questionStmt.executeQuery();

            List<Question> questions = new ArrayList<>();
            while (questionRs.next()) {
                int id = questionRs.getInt("id");
                String questionText = questionRs.getString("question_text");
                String answer = questionRs.getString("answer");
                int marks = questionRs.getInt("marks");
                questions.add(new Question(id, questionText, answer, marks));
            }

            // Randomly select 10 questions
            Collections.shuffle(questions);
            List<Question> randomQuestions = questions.subList(0, Math.min(10, questions.size()));

            System.out.println("Starting challenge...");
            int score = 0;
            int participantId = 1; // Use actual participant_id
            int attemptId = 0;

            // Insert the attempt record
            String insertAttemptQuery = "INSERT INTO Attempts (participant_id, challenge_id, score, time_taken) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertAttemptStmt = conn.prepareStatement(insertAttemptQuery,
                    PreparedStatement.RETURN_GENERATED_KEYS)) {
                insertAttemptStmt.setInt(1, participantId);
                insertAttemptStmt.setInt(2, challengeNumber);
                insertAttemptStmt.setInt(3, 0); // Initial score, will be updated later
                insertAttemptStmt.setTime(4, new Time(System.currentTimeMillis())); // Use actual time taken
                insertAttemptStmt.executeUpdate();

                ResultSet generatedKeys = insertAttemptStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    attemptId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating attempt failed, no ID obtained.");
                }
            }

            // Iterate through the selected questions
            for (Question question : randomQuestions) {
                System.out.println(question.getQuestionText());
                String userAnswer = scanner.nextLine();

                int marksAwarded = 0;
                if (userAnswer.equalsIgnoreCase(question.getAnswer())) {
                    marksAwarded = question.getMarks();
                    score += marksAwarded;
                } else if (userAnswer.equals("-")) {
                    marksAwarded = 0;
                } else {
                    marksAwarded = -3;
                    score += marksAwarded;
                }

                // Insert marks into Marks table
                String insertMarksQuery = "INSERT INTO Marks (attempt_id, question_id, marks_awarded) VALUES (?, ?, ?)";
                try (PreparedStatement insertMarksStmt = conn.prepareStatement(insertMarksQuery)) {
                    insertMarksStmt.setInt(1, attemptId);
                    insertMarksStmt.setInt(2, question.getId());
                    insertMarksStmt.setInt(3, marksAwarded);
                    insertMarksStmt.executeUpdate();
                }
            }

            // Update the score and time taken in the Attempts table
            String updateAttemptQuery = "UPDATE Attempts SET score = ?, time_taken = ? WHERE id = ?";
            try (PreparedStatement updateAttemptStmt = conn.prepareStatement(updateAttemptQuery)) {
                updateAttemptStmt.setInt(1, score);
                updateAttemptStmt.setTime(2, new Time(System.currentTimeMillis())); // Update with actual time taken
                updateAttemptStmt.setInt(3, attemptId);
                updateAttemptStmt.executeUpdate();
            }

            System.out.println("Challenge completed. Your score: " + score);
            writer.println("ATTEMPT_CHALLENGE:" + challengeNumber + "," + score);
            System.out.println("Attempt recorded successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static class Question {
        private final int id;
        private final String questionText;
        private final String answer;
        private final int marks;

        public Question(int id, String questionText, String answer, int marks) {
            this.id = id;
            this.questionText = questionText;
            this.answer = answer;
            this.marks = marks;
        }

        public int getId() {
            return id;
        }

        public String getQuestionText() {
            return questionText;
        }

        public String getAnswer() {
            return answer;
        }

        public int getMarks() {
            return marks;
        }
    }
}
