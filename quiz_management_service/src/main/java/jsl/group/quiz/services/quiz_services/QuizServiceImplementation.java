package jsl.group.quiz.services.quiz_services;

import jsl.group.quiz.models.entities.*;
import jsl.group.quiz.models.scores.AveragePerformance;
import jsl.group.quiz.models.scores.ReviewPersistence;
import jsl.group.quiz.models.scores.ScoreBoard;
import jsl.group.quiz.models.scores.TotalScore;
import jsl.group.quiz.services.admin_services.QuestionServices;
import jsl.group.quiz.singletons.DataCachedCenter;
import jsl.group.quiz.singletons.SingleQuestionService;
import jsl.group.quiz.utils.structures.PositionalLinkedList;

import java.util.List;

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
    public List<String> endQuiz(String username, String subject) {
        ScoreBoard scoreBoard = ScoreBoard.getInstance();
        ReviewPersistence reviewPersistence = ReviewPersistence.getInstance(scoreBoard);
        TotalScore totalScore = TotalScore.getInstance(scoreBoard);
        AveragePerformance averagePerformance = AveragePerformance.getInstance(scoreBoard);
        List<String> questionIds = DataCachedCenter.INSTANCE.getDataCenter().quizAnswers.get(username).stream().map(QuizAnswer::questionId).toList();
        UserScoreAndReview userScoreAndReview = new UserScoreAndReview.Builder()
                .subject(subject).username(username).addQuestionIds(questionIds)
                .build();
        List<String> results = scoreBoard.valueChanges(userScoreAndReview);
        DataCachedCenter.INSTANCE.getDataCenter().quizQuestions.remove(username);
        DataCachedCenter.INSTANCE.getDataCenter().quizAnswers.remove(username);
        DataCachedCenter.INSTANCE.getDataCenter().userAnswerChoices.remove(username);
        return results;
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
