package jsl.group.quiz.services.quiz_services;

import jsl.group.quiz.models.Question;
import jsl.group.quiz.models.Quiz;
import jsl.group.quiz.models.QuizQuestion;
import jsl.group.quiz.services.admin_services.QuestionServices;
import jsl.group.quiz.singletons.DataCachedCenter;
import jsl.group.quiz.singletons.SingleQuestionService;
import jsl.group.quiz.utils.structures.PositionalLinkedList;

public class QuizServiceImplementation implements QuizServices {
    private final QuestionServices questionServices = SingleQuestionService.INSTANCE.getInstance();
    private QuizServiceImplementation() {}
    public static QuizServiceImplementation getInstance() {
        return new QuizServiceImplementation();
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
    public PositionalLinkedList<QuizQuestion> quizQuestions(String subject, int limit, String username) {
        Quiz quiz = questionServices.quizQuestions(subject, limit);
        DataCachedCenter.INSTANCE.getDataCenter().quizAnswers.put(username, quiz.quizAnswers());
        DataCachedCenter.INSTANCE.getDataCenter().quizQuestions.put(username, quiz.quizQuestions());
        return quiz.quizQuestions();
    }
}
