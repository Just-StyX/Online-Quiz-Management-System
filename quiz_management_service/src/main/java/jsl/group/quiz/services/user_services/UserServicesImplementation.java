package jsl.group.quiz.services.user_services;

import jsl.group.quiz.models.entities.FoundUser;
import jsl.group.quiz.models.entities.UserLogin;
import jsl.group.quiz.models.entities.UserRegistration;
import jsl.group.quiz.singletons.SingleUserRegistration;
import jsl.group.quiz.utils.exceptions.UniquenessException;
import jsl.group.quiz.utils.exceptions.ValidationException;

import java.sql.SQLException;

public class UserServicesImplementation implements UserServices {
    private final UserRegistrationServices userRegistrationServices = SingleUserRegistration.INSTANCE.getInstance();
    private UserServicesImplementation() {}
    public static UserServicesImplementation getInstance() {
        return new UserServicesImplementation();
    }
    @Override
    public String userRegistration(UserRegistration userRegistration) throws SQLException {
        if (validate(userRegistration)) throw new ValidationException("Fields cannot be empty");
        if (userRegistration.password().length() < 8) throw new ValidationException("Password must be at least characters");
        if (userRegistrationServices.nonUniqueness(userRegistration.username(), userRegistration.email())) {
            throw new UniquenessException("Username or Email is not available");
        }
        return userRegistrationServices.register(userRegistration);
    }
    @Override
    public FoundUser userLogin(UserLogin userLogin) throws SQLException {
        return userRegistrationServices.login(userLogin);
    }

    private boolean validate(UserRegistration userRegistration) {
        return (
                userRegistration.email().isEmpty() ||
                        userRegistration.username().isEmpty() ||
                        userRegistration.firstName().isEmpty() ||
                        userRegistration.password().isEmpty() ||
                        userRegistration.lastName().isEmpty()
                );
    }
}
