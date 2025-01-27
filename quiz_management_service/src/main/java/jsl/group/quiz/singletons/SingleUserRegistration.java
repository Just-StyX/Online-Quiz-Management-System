package jsl.group.quiz.singletons;

import jsl.group.quiz.services.user_services.UserRegistrationServiceImplementation;
import jsl.group.quiz.services.user_services.UserRegistrationServices;

public enum SingleUserRegistration {
    INSTANCE;

    private final UserRegistrationServices userRegistrationServices;

    SingleUserRegistration() {
        userRegistrationServices = new UserRegistrationServiceImplementation();
    }

    public UserRegistrationServices getInstance() { return userRegistrationServices; }
}
