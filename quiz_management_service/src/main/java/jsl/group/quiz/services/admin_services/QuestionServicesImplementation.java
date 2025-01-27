package jsl.group.quiz.services.admin_services;

import jsl.group.quiz.config.DataBaseConfiguration;
import jsl.group.quiz.models.Question;
import jsl.group.quiz.models.Quiz;
import jsl.group.quiz.models.QuizAnswer;
import jsl.group.quiz.models.QuizQuestion;
import jsl.group.quiz.utils.DataUtilities;
import jsl.group.quiz.utils.Level;
import jsl.group.quiz.utils.structures.Position;
import jsl.group.quiz.utils.structures.PositionalLinkedList;
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
            preparedStatement.setString(1, question.question().trim());
            preparedStatement.setString(2, question.answer().trim());
            preparedStatement.setObject(3, question.options());
            preparedStatement.setString(4, question.subject().trim());
            preparedStatement.setDouble(5, question.points());
            preparedStatement.setString(6, question.level().trim());
            preparedStatement.setObject(7, UUID.fromString(questionId));

            int affectRows = preparedStatement.executeUpdate();
            if (affectRows == 1) return "Updated question with id " + questionId + " successfully";
            else return "Number of rows updated is 0";
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
                Map<Character, String> mapOptions = new TreeMap<>();
                String options = resultSet.getString("options");
                if (!options.isBlank()) {
                    Arrays.stream(options.split(",")).forEach(string -> {
                        String[] parts = string.split("=>");
                        mapOptions.put(parts[0].charAt(0), parts[1]);
                    });
                }
                return new Question(
                        resultSet.getString("question_id"),
                        resultSet.getString("question"),
                        resultSet.getString("answer"),
                        mapOptions,
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
            PositionalLinkedList<QuizQuestion> quizQuestions = PositionalLinkedList.getInstance();
            List<QuizAnswer> quizAnswers = new ArrayList<>();
            while (resultSet.next()) {
                Map<Character, String> mapOptions = new TreeMap<>();
                String options = resultSet.getString("options").replace("\"", "");
                if (!options.isBlank()) {
                    Arrays.stream(options.split(", ")).forEach(string -> {
                        String[] parts = string.split("=>");
                        mapOptions.put(parts[0].charAt(0), parts[1]);
                    });
                }
                quizQuestions.addLast(new QuizQuestion(
                        resultSet.getString("question"),
                        mapOptions,
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
        return new Quiz(PositionalLinkedList.getInstance(), List.of());
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
//        List<QuizQuestion> quizQuestions = new ArrayList<>();
        PositionalLinkedList<QuizQuestion> quizQuestions = PositionalLinkedList.getInstance();
        for (Position<QuizQuestion> position: easy.quizQuestions()) quizQuestions.addFirst(position.getElement());
        for (Position<QuizQuestion> position: medium.quizQuestions()) quizQuestions.addFirst(position.getElement());
        for (Position<QuizQuestion> position: hard.quizQuestions()) quizQuestions.addFirst(position.getElement());
        List<QuizAnswer> quizAnswers = new ArrayList<>();
//        quizQuestions.addAll(easy.quizQuestions());
//        quizQuestions.addAll(medium.quizQuestions());
//        quizQuestions.addAll(hard.quizQuestions());
//        Collections.shuffle(quizQuestions);
//        quizQuestions.shuffle();
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
                    delete from questions where question_id = ?
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setObject(1, UUID.fromString(questionId));

            int affectRows = preparedStatement.executeUpdate();
            if (affectRows == 1) return "Deleted question with id " + questionId + " successfully";
            else return "Number of rows deleted is 0";
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return "";
    }

    @Override
    public Map<String, String> findQuestionByQuestion(String question) {
        try {
            Connection connection = DataBaseConfiguration.getConnection();
            String query = """
                    select * from questions where question = ?
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, question.trim());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Map<String, String> mapOptions = new TreeMap<>();

                // TODO: change these replacements as they are causing trailing ';'
                String options = resultSet.getString("options").replace("\"", "")
                        .replace("=>", "=").replace(", ", ";");

                mapOptions.put("questionId", resultSet.getString("question_id"));
                mapOptions.put("question", resultSet.getString("question"));
                mapOptions.put("answer", resultSet.getString("answer"));
                mapOptions.put("options", options);
                mapOptions.put("subject", resultSet.getString("subject"));
                mapOptions.put("points", String.valueOf(resultSet.getDouble("points")));
                mapOptions.put("level", resultSet.getString("level"));
                return mapOptions;
            } else {
                return DataUtilities.emptyData();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
