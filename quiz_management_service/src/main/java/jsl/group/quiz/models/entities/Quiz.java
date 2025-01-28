package jsl.group.quiz.models.entities;

import jsl.group.quiz.utils.structures.PositionalLinkedList;

import java.util.List;

public record Quiz(PositionalLinkedList<QuizQuestion> quizQuestions, List<QuizAnswer> quizAnswers) {
}
