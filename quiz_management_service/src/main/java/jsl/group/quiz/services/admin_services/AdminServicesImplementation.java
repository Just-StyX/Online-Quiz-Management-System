package jsl.group.quiz.services.admin_services;

import jsl.group.quiz.models.entities.Question;
import jsl.group.quiz.models.entities.Quiz;
import jsl.group.quiz.singletons.SingleQuestionService;
import jsl.group.quiz.utils.exceptions.ValidationException;

import java.sql.SQLException;
import java.util.Map;

public class AdminServicesImplementation implements AdminServices{
    private final QuestionServices questionServices = SingleQuestionService.INSTANCE.getInstance();

    private AdminServicesImplementation() {}
    public static AdminServicesImplementation getInstance() {
        return new AdminServicesImplementation();
    }
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
