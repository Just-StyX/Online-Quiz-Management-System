package jsl.group.quiz.services.quiz_services;

import jsl.group.quiz.models.entities.Question;
import jsl.group.quiz.models.entities.Quiz;
import jsl.group.quiz.models.entities.QuizQuestion;
import jsl.group.quiz.utils.structures.PositionalLinkedList;

import java.util.List;

public interface QuizServices {
    Quiz findQuestions(String subject, String level, int limit);
    PositionalLinkedList<QuizQuestion> quizQuestions(String subject, int limit, String username);
    Question findQuestionById(String questionId);
    List<String> endQuiz(String username, String subject);
}
