package jsl.group.quiz.models.entities;

public record QuizAnswer(String questionId, String answer, String level, double points, String subject) {
}
