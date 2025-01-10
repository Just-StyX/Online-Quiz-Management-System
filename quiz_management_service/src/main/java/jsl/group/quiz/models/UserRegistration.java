package jsl.group.quiz.models;

public record UserRegistration(
        String username,
        String password,
        String email,
        String firstName,
        String middleName,
        String lastName,
        String uniqueId,
        String role
) {
}
