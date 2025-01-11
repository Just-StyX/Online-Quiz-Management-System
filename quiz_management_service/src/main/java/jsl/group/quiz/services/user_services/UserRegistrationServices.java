package jsl.group.quiz.services.user_services;

import jsl.group.quiz.models.FoundUser;
import jsl.group.quiz.models.UserLogin;
import jsl.group.quiz.models.UserRegistration;

import java.sql.SQLException;

public interface UserRegistrationServices {
    String register(UserRegistration userRegistration) throws SQLException;
    FoundUser login(UserLogin userLogin) throws SQLException;
    boolean nonUniniqueness(String username, String email) throws SQLException;
}
