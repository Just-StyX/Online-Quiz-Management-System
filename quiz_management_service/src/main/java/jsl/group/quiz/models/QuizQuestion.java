package jsl.group.quiz.models;

import java.util.Map;
import java.util.Objects;

public record QuizQuestion(
        String question,
        Map<Character, String> options,
        double points,
        String questionId
) {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        QuizQuestion that = (QuizQuestion) o;
        return Double.compare(points, that.points) == 0 && Objects.equals(question, that.question) &&
                Objects.equals(questionId, that.questionId) && Objects.equals(options, that.options);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, options, points, questionId);
    }
}
