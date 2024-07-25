
package Only;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Questions {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tonly";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private static final int NUM_QUESTIONS = 10;
    private static final int QUIZ_DURATION_MINUTES = 30;
    private static final long QUIZ_DURATION_MILLIS = QUIZ_DURATION_MINUTES * 60 * 1000;

    public List<Question> getRandomQuestions() {
        List<Question> questions = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT id, questions FROM questions ORDER BY RAND() LIMIT ?;")) {
            stmt.setInt(1, NUM_QUESTIONS);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String questionText = rs.getString("questions");
                    questions.add(new Question(id, questionText));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public boolean validateAnswers(List<Answer> userAnswers) {
        boolean allCorrect = true;
        String query = "SELECT id, answers FROM answers WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            for (Answer userAnswer : userAnswers) {
                stmt.setInt(1, userAnswer.getQuestionId());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String correctAnswer = rs.getString("answers");
                        if (!correctAnswer.equalsIgnoreCase(userAnswer.getAnswerText())) {
                            allCorrect = false;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            allCorrect = false;
        }
        return allCorrect;
    }

    public static void main(String[] args) {
        Questions questionService = new Questions();
        List<Question> questions = questionService.getRandomQuestions();
        List<Answer> userAnswers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        long startTime = System.currentTimeMillis();
        long endTime = startTime + QUIZ_DURATION_MILLIS;

        System.out.println("Welcome to the Quiz! You have 30 minutes to complete.");

        // Display questions
        for (Question question : questions) {
            if (System.currentTimeMillis() > endTime) {
                System.out.println("\nTime's up! Quiz ended.");
                break;
            }
            System.out.println(question.getQuestionText());
            String userAnswer = scanner.nextLine().trim();
            userAnswers.add(new Answer(question.getId(), userAnswer));

            // Calculate and display remaining time
            long currentTime = System.currentTimeMillis();
            long remainingTime = endTime - currentTime;
            long remainingMinutes = remainingTime / (60 * 1000);
            long remainingSeconds = (remainingTime / 1000) % 60;

            System.out.printf("Time remaining: %d minutes, %d seconds\n", remainingMinutes, remainingSeconds);
        }

        // Validate answers
        boolean allCorrect = questionService.validateAnswers(userAnswers);

        // Calculate marks
        int totalQuestions = questions.size();
        int correctAnswers = allCorrect ? totalQuestions : 0;
        double marks = (double) correctAnswers / totalQuestions * 100;

        System.out.println("\nQuiz Results:");
        System.out.println("Total Questions: " + totalQuestions);
        System.out.println("Correct Answers: " + correctAnswers);
        System.out.printf("Marks Obtained: %.2f%%\n", marks);

        scanner.close();
    }
}

class Question {
    private int id;
    private String questions;

    public Question(int id, String questions) {
        this.id = id;
        this.questions = questions;
    }

    public int getId() {
        return id;
    }

    public String getQuestionText() {
        return questions;
    }
}

class Answer {
    private int questionId;
    private String answerText;

    public Answer(int questionId, String answerText) {
        this.questionId = questionId;
        this.answerText = answerText;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getAnswerText() {
        return answerText;
    }
}




