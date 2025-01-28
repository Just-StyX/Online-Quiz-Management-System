package jsl.group.quiz.models.entities;

import java.util.Map;

public record Question(
        String questionId,
        String question,
        String answer,
        Map<Character, String> options,
        String subject,
        double points,
        String level
) {
}
