package jsl.group.quiz.services.user_services;

import jsl.group.quiz.models.FoundUser;
import jsl.group.quiz.models.UserLogin;
import jsl.group.quiz.models.UserRegistration;

import java.sql.SQLException;

public interface UserServices {
    String userRegistration(UserRegistration userRegistration) throws SQLException;
    FoundUser userLogin(UserLogin userLogin) throws SQLException;
}
