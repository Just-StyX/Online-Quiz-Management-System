package jsl.group.quiz.models.scores;

import jsl.group.quiz.models.entities.UserScoreAndReview;
import jsl.group.quiz.singletons.DatabaseConnection;

import java.sql.*;

public class ReviewPersistence implements Observer, PerformanceDisplay {
    private ReviewPersistence(ScoreBoard scoreBoard) {
        scoreBoard.addObserver(this);
    }
    public static ReviewPersistence getInstance(ScoreBoard scoreBoard) {
        return new ReviewPersistence(scoreBoard);
    }

    @Override
    public String update(UserScoreAndReview userScoreAndReview) {
        String message = "";
        String username = userScoreAndReview.getUsername();
        for (String questionId: userScoreAndReview.getQuestionIds()) {
            message = revision(username, questionId);
            if (message == null) break;
        }
        return display(message + " records updated successfully");
    }

    @Override
    public String display(String message) {
        return message;
    }

    private String revision(String username, String questionId) {
        try {
            Connection connection = DatabaseConnection.INSTANCE.getConnection();
            String query = """
                    insert into revision (username, question_id) values (?, ?)
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2, questionId);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
