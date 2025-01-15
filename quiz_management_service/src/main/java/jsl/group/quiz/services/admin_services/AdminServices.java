package jsl.group.quiz.services.admin_services;

import jsl.group.quiz.models.FoundUser;
import jsl.group.quiz.models.Question;
import jsl.group.quiz.models.Quiz;
import jsl.group.quiz.models.UserLogin;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface AdminServices {
    String createQuestion(Question question) throws SQLException;
    String updateQuestionById(String questionId, Question question) throws SQLException;
    Question findQuestionById(String questionId);
    Quiz findQuestions(String subject, String level, int limit);
    Quiz quizQuestions(String subject, int limit);
    String deleteQuestionById(String questionId);
    Map<String, String> findQuestionByQuestion(String question);
}
