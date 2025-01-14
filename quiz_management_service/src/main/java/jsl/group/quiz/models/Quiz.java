package jsl.group.quiz.models;

import java.util.List;

public record Quiz(List<QuizQuestion> quizQuestions, List<QuizAnswer> quizAnswers) {
}
