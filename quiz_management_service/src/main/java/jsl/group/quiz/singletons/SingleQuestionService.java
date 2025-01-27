package jsl.group.quiz.singletons;

import jsl.group.quiz.services.admin_services.QuestionServices;
import jsl.group.quiz.services.admin_services.QuestionServicesImplementation;

public enum SingleQuestionService {
    INSTANCE;

    private final QuestionServices questionServices;

    SingleQuestionService() {
        questionServices = new QuestionServicesImplementation();
    }

    public QuestionServices getInstance() { return questionServices; }
}
