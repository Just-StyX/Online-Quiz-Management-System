package jsl.group.quiz.services.admin_services;

import jsl.group.quiz.models.Question;
import jsl.group.quiz.models.Quiz;
import jsl.group.quiz.utils.exceptions.ValidationException;

import java.sql.SQLException;
import java.util.Map;

public class AdminServicesImplementation implements AdminServices{
    private final QuestionServices questionServices = new QuestionServicesImplementation();
    @Override
    public String createQuestion(Question question) throws SQLException {
        if (validate(question)) throw new ValidationException("Fields cannot be null or empty");
        return questionServices.createQuestion(question);
    }

    @Override
    public String updateQuestionById(String questionId, Question question) throws SQLException {
        if (validate(question)) throw new ValidationException("Fields cannot be null or empty");
        return questionServices.updateQuestionById(questionId, question);
    }

    @Override
    public Question findQuestionById(String questionId) {
        return questionServices.findQuestionById(questionId);
    }

    @Override
    public Quiz findQuestions(String subject, String level, int limit) {
        return questionServices.findQuestions(subject, level, limit);
    }

    @Override
    public Quiz quizQuestions(String subject, int limit) {
        return questionServices.quizQuestions(subject, limit);
    }

    @Override
    public String deleteQuestionById(String questionId) {
        return questionServices.deleteQuestionById(questionId);
    }

    @Override
    public Map<String, String> findQuestionByQuestion(String question) {
        return questionServices.findQuestionByQuestion(question);
    }

    private boolean validate(Question question) {
        return (
                question.question().isEmpty() ||
                        question.answer().isEmpty() ||
                        question.level().isEmpty() ||
                        question.subject().isEmpty()
                );
    }
}
