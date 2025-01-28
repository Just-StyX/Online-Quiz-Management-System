package jsl.group.quiz.models.scores;

import jsl.group.quiz.config.DataBaseConfiguration;
import jsl.group.quiz.models.entities.UserScoreAndReview;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

public class AveragePerformance implements Observer, PerformanceDisplay {
    private AveragePerformance(ScoreBoard scoreBoard) {
        scoreBoard.addObserver(this);
    }
    public static AveragePerformance getInstance(ScoreBoard scoreBoard) {
        return new AveragePerformance(scoreBoard);
    }

    @Override
    public String update(UserScoreAndReview userScoreAndReview) {
        String message;
        try {
            List<Double> scores = new ArrayList<>();
            ResultSet resultSet = getResultSet(userScoreAndReview.getSubject(), userScoreAndReview.getUsername());
            while (resultSet.next()) {
                scores.add(resultSet.getDouble("score"));
            }
            DoubleSummaryStatistics summaryStatistics = scores.stream().mapToDouble(data -> data).summaryStatistics();
            message = String.format("min: %.2f; max: %.2f; average: %.2f",
                    summaryStatistics.getMin(), summaryStatistics.getMax(), summaryStatistics.getAverage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return display(message);
    }

    @Override
    public String display(String message) {
        return message;
    }

    private ResultSet getResultSet(String subject, String username) throws SQLException {
        Connection connection = DataBaseConfiguration.getConnection();
        String query = """
                select * from scores where subject = ? and username = ?
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, subject);
        preparedStatement.setString(2, username);
        return preparedStatement.executeQuery();
    }
}
