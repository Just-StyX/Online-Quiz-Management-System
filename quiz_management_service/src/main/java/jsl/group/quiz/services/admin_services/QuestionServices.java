package jsl.group.quiz.services.admin_services;

import jsl.group.quiz.models.Question;
import jsl.group.quiz.models.Quiz;

import java.sql.SQLException;
import java.util.List;

public interface QuestionServices {
    String createQuestion(Question question) throws SQLException;
    String updateQuestionById(String questionId, Question question) throws SQLException;
    Question findQuestionById(String questionId);
    Quiz findQuestions(String subject, String level, int limit);
    Quiz quizQuestions(String subject, int limit);
    String deleteQuestionById(String questionId);
}
