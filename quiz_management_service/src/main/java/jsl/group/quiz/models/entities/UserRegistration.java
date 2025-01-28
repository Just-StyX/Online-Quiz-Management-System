package jsl.group.quiz.models.entities;

public record UserRegistration(
        String username,
        String password,
        String email,
        String firstName,
        String middleName,
        String lastName
) {
}
