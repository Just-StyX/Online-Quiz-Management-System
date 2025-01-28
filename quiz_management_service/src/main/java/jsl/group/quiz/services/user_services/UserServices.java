package jsl.group.quiz.services.user_services;

import jsl.group.quiz.models.entities.FoundUser;
import jsl.group.quiz.models.entities.UserLogin;
import jsl.group.quiz.models.entities.UserRegistration;

import java.sql.SQLException;

public interface UserServices {
    String userRegistration(UserRegistration userRegistration) throws SQLException;
    FoundUser userLogin(UserLogin userLogin) throws SQLException;
}
