package Only;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
// import java.util.Random;
//import java.util.Random;
import java.util.Scanner;

public class Questions{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tonly";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private static final int NUM_QUESTIONS = 10;

    public List<Question> getRandomQuestions() {



        
        List<Question> questions = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "SELECT id, questions FROM questions ORDER BY RAND() LIMIT ?;";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, NUM_QUESTIONS);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String questionText = rs.getString("questions");
                Question question = new Question(id, questionText);
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return questions;
    }

    public boolean validateAnswers(List<Answer> userAnswers) {
        // Random rand = new Random();

        boolean allCorrect = true;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String query = "SELECT id, answers FROM answers WHERE id = ";
            

            for (Answer userAnswer : userAnswers) {
                 query+=userAnswer.questionId;
                stmt = conn.prepareStatement(query);

                stmt.setInt(1, userAnswer.getQuestionId());
                rs = stmt.executeQuery();

                if (rs.next()) {
                    String correctAnswer = rs.getString("answers");
                    String userEnteredAnswer = userAnswer.getAnswerText();

                    if (!correctAnswer.equalsIgnoreCase(userEnteredAnswer)) {
                        allCorrect = false;
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            allCorrect = false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return allCorrect;
    }
    public static void main(String[] args) {
        Questions questionService = new Questions();
        List<Question> questions = questionService.getRandomQuestions();
        List<Answer> userAnswers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Quiz!\n");

        // Display questions
        for (Question question : questions) {
            System.out.println(question.getQuestionText());
            String userAnswer = scanner.nextLine().trim();
            userAnswers.add(new Answer(question.getId(), userAnswer));
        }

        // Validate answers
        boolean allCorrect = questionService.validateAnswers(userAnswers);

        // Calculate marks
        int totalQuestions = questions.size();
        double marks = allCorrect ? (double) userAnswers.size() / totalQuestions * 100 : 0;

        System.out.println("\nQuiz Results:");
        System.out.println("Total Questions: " + totalQuestions);
        System.out.println("Correct Answers: " + (allCorrect ? totalQuestions : 0));
        System.out.printf("Marks Obtained: %.2f%%\n", marks);

        scanner.close();
    }


}

class Question {
    public int id;
    public String questions;

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
    public int questionId;
    public String answerText;

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


