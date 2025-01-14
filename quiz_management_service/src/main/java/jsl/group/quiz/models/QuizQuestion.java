package jsl.group.quiz.models;

import java.util.Map;

public record QuizQuestion(
        String question,
        Map<Character, String> options,
        double points,
        String questionId
) {
}
