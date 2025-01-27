package jsl.group.quiz.services.quiz_services;

import jsl.group.quiz.models.Question;
import jsl.group.quiz.models.Quiz;
import jsl.group.quiz.models.QuizQuestion;
import jsl.group.quiz.utils.structures.PositionalLinkedList;

import java.util.List;

public interface QuizServices {
    Quiz findQuestions(String subject, String level, int limit);
    PositionalLinkedList<QuizQuestion> quizQuestions(String subject, int limit, String username);
    Question findQuestionById(String questionId);
}
