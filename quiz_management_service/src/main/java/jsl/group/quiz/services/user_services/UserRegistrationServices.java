package jsl.group.quiz.services.user_services;

import jsl.group.quiz.models.entities.FoundUser;
import jsl.group.quiz.models.entities.UserLogin;
import jsl.group.quiz.models.entities.UserRegistration;

import java.sql.SQLException;

public interface UserRegistrationServices {
    String register(UserRegistration userRegistration) throws SQLException;
    FoundUser login(UserLogin userLogin) throws SQLException;
    boolean nonUniqueness(String username, String email) throws SQLException;
}
