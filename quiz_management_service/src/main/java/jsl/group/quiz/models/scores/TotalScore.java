package jsl.group.quiz.models.scores;

import jsl.group.quiz.models.entities.QuizAnswer;
import jsl.group.quiz.models.entities.UserScoreAndReview;
import jsl.group.quiz.singletons.DataCachedCenter;
import jsl.group.quiz.singletons.DatabaseConnection;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class TotalScore implements Observer, PerformanceDisplay {
    private TotalScore(ScoreBoard scoreBoard) {
        scoreBoard.addObserver(this);
    }
    public static TotalScore getInstance(ScoreBoard scoreBoard) {
        return new TotalScore(scoreBoard);
    }

    @Override
    public String update(UserScoreAndReview userScoreAndReview) {
        String username = userScoreAndReview.getUsername();
        double score = 0;
        double totalAvailablePoints = 0;
        String message = String.format("Total score: %.2f; Total Available: %.2f", score, totalAvailablePoints);
        List<QuizAnswer> quizAnswers = DataCachedCenter.INSTANCE.getDataCenter().quizAnswers.get(username);
        Map<String, Character> userAnswers = DataCachedCenter.INSTANCE.getDataCenter().userAnswerChoices.get(username);
        if (userAnswers == null) {
            return message;
        };
        for (QuizAnswer quizAnswer: quizAnswers) {
            String questionId = quizAnswer.questionId();
            if (userAnswers.get(questionId) != null && userAnswers.get(questionId).equals(quizAnswer.answer().charAt(0))) {
                score += quizAnswer.points();
            }
            totalAvailablePoints += quizAnswer.points();
        }
        enterScores(username, userScoreAndReview.getSubject(), score);
        message = String.format("Total score: %.2f; Total Available: %.2f", score, totalAvailablePoints);
        return display(message);
    }

    @Override
    public String display(String message) {
        return message;
    }

    private void enterScores(String username, String subject, double score) {
        try {
            Connection connection = DatabaseConnection.INSTANCE.getConnection();
            String query = """
                    insert into scores (username, subject, score) values (?, ?, ?)
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2, subject);
            preparedStatement.setDouble(3, score);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                resultSet.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
