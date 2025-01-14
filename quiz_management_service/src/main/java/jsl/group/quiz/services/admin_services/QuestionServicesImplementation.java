package jsl.group.quiz.services.admin_services;

import jsl.group.quiz.config.DataBaseConfiguration;
import jsl.group.quiz.models.Question;
import jsl.group.quiz.models.Quiz;
import jsl.group.quiz.models.QuizAnswer;
import jsl.group.quiz.models.QuizQuestion;
import jsl.group.quiz.utils.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

public class QuestionServicesImplementation implements QuestionServices {
    private static final Logger log = LoggerFactory.getLogger(QuestionServicesImplementation.class);
    @Override
    public String createQuestion(Question question) {
        try {
            Connection connection = DataBaseConfiguration.getConnection();
            String query = """
                    insert into questions (question, answer, options, subject, points, level) values (?, ?, ?, ?, ?, ?)
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, question.question());
            preparedStatement.setString(2, question.answer());
            preparedStatement.setObject(3, question.options());
            preparedStatement.setString(4, question.subject());
            preparedStatement.setDouble(5, question.points());
            preparedStatement.setString(6, question.level());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return "";
    }

    @Override
    public String updateQuestionById(String questionId, Question question) {
        try {
            Connection connection = DataBaseConfiguration.getConnection();
            String query = """
                    update questions set question = ?, answer = ?, options = ?, subject = ?, points = ?, level = ? where question_id = ?
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, question.question());
            preparedStatement.setString(2, question.answer());
            preparedStatement.setObject(3, question.options());
            preparedStatement.setString(4, question.subject());
            preparedStatement.setDouble(5, question.points());
            preparedStatement.setString(6, question.level());
            preparedStatement.setObject(7, UUID.fromString(questionId));

            int affectRows = preparedStatement.executeUpdate();
            if (affectRows == 1) return "Updated question with id " + questionId + " successfully";
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return "";
    }

    @Override
    public Question findQuestionById(String questionId) {
        try {
            Connection connection = DataBaseConfiguration.getConnection();
            String query = "select * from questions where question_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, UUID.fromString(questionId));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Question(
                        resultSet.getString("question_id"),
                        resultSet.getString("question"),
                        resultSet.getString("answer"),
                        resultSet.getObject("options", LinkedHashMap.class),
                        resultSet.getString("subject"),
                        resultSet.getDouble("points"),
                        resultSet.getString("level")
                );
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return new Question(questionId, "", "", null, "", 0.0, "");
    }
    @Override
    public Quiz findQuestions(String subject, String level, int limit) {
        try {
            ResultSet resultSet = getResultSet(subject, level, limit);
            List<QuizQuestion> quizQuestions = new ArrayList<>();
            List<QuizAnswer> quizAnswers = new ArrayList<>();
            while (resultSet.next()) {
                quizQuestions.add(new QuizQuestion(
                        resultSet.getString("question"),
                        resultSet.getObject("options", LinkedHashMap.class),
                        resultSet.getDouble("points"),
                        resultSet.getString("question_id")
                ));
                quizAnswers.add(new QuizAnswer(
                        resultSet.getString("question_id"),
                        resultSet.getString("answer"),
                        resultSet.getString("level")
                ));
            }
            return new Quiz(quizQuestions, quizAnswers);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return new Quiz(List.of(), List.of());
    }
    @Override
    public Quiz quizQuestions(String subject, int limit) {
        int levelLimits = limit / 3;
        int difference = limit - (levelLimits * 3);
        if (limit < 3) {
            return findQuestions(subject, Level.MEDIUM.getLevel(), limit);
        }
        Quiz hard = findQuestions(subject, Level.HARD.getLevel(), levelLimits);
        Quiz medium = findQuestions(subject, Level.MEDIUM.getLevel(), levelLimits + difference);
        Quiz easy = findQuestions(subject, Level.EASY.getLevel(), levelLimits);
        List<QuizQuestion> quizQuestions = new ArrayList<>();
        List<QuizAnswer> quizAnswers = new ArrayList<>();
        quizQuestions.addAll(easy.quizQuestions());
        quizQuestions.addAll(medium.quizQuestions());
        quizQuestions.addAll(hard.quizQuestions());
        Collections.shuffle(quizQuestions);
        quizAnswers.addAll(easy.quizAnswers());
        quizAnswers.addAll(medium.quizAnswers());
        quizAnswers.addAll(hard.quizAnswers());
        return new Quiz(quizQuestions, quizAnswers);
    }

    @Override
    public String deleteQuestionById(String questionId) {
        try {
            Connection connection = DataBaseConfiguration.getConnection();
            String query = """
                    delete * from questions where question_id = ?
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, questionId);

            int affectRows = preparedStatement.executeUpdate();
            if (affectRows == 1) return "Deleted question with id " + questionId + " successfully";
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return "";
    }
    private ResultSet getResultSet(String subject, String level, int limit) throws SQLException {
        Connection connection = DataBaseConfiguration.getConnection();
        String query = """
                select * from questions where subject = ? and level = ? order by random() limit ?
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, subject);
        preparedStatement.setString(2, level);
        preparedStatement.setInt(3, limit);
        return preparedStatement.executeQuery();
    }
}
