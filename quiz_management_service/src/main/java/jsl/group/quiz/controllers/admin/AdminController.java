package jsl.group.quiz.controllers.admin;

import io.javalin.http.Handler;
import jsl.group.quiz.models.Question;
import jsl.group.quiz.models.Quiz;
import jsl.group.quiz.services.admin_services.AdminServices;
import jsl.group.quiz.services.admin_services.AdminServicesImplementation;
import jsl.group.quiz.utils.exceptions.UnauthorizedPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static jsl.group.quiz.utils.RoutesAuthorization.isAdmin;
import static jsl.group.quiz.utils.RoutesAuthorization.isUserOrAdmin;

public class AdminController {
    private static final AdminServices adminServices = new AdminServicesImplementation();
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    public static Handler createQuestion = context -> {
        if (isAdmin(context)) {
            Question question = context.bodyAsClass(Question.class);
            String result = adminServices.createQuestion(question);
            context.json(result);
        } else {
            throw new UnauthorizedPath("access denied");
        }
    };

    public static Handler updateQuestion = context -> {
        if (isAdmin(context)) {
            String questionId = context.queryParam("id");
            Question question = context.bodyAsClass(Question.class);
            String result = adminServices.updateQuestionById(questionId, question);
            context.json(result);
        } else {
            throw new UnauthorizedPath("access denied");
        }
    };

    public static Handler getQuestionById = context -> {
        if (isAdmin(context)) {
            String questionId = context.queryParam("id");
            Question question = adminServices.findQuestionById(questionId);
            context.json(question);
        } else {
            throw new UnauthorizedPath("access denied");
        }
    };

    public static Handler deleteQuestionById = context -> {
        if (isAdmin(context)) {
            String questionId = context.queryParam("id");
            String result = adminServices.deleteQuestionById(questionId);
            context.json(result);
        } else {
            throw new UnauthorizedPath("access denied");
        }
    };

    public static Handler getPracticeQuestions = context -> {
        if (isUserOrAdmin(context)) {
            Map<String, List<String>> parameters = context.queryParamMap();
            String subject = parameters.get("subject").getFirst();
            String level = parameters.get("level").getFirst();
            int limit = Integer.parseInt(parameters.get("limit").getFirst());
            Quiz practiceQuestions = adminServices.findQuestions(subject, level, limit);
            context.json(practiceQuestions);
        } else {
            throw new UnauthorizedPath("access denied");
        }
    };

    public static Handler quizQuestions = context -> {
        if (isUserOrAdmin(context)) {
            Map<String, List<String>> parameters = context.queryParamMap();
            String subject = parameters.get("subject").getFirst();
            int limit = Integer.parseInt(parameters.get("limit").getFirst());
            Quiz practiceQuestions = adminServices.quizQuestions(subject, limit);
            context.json(practiceQuestions);
        } else {
            throw new UnauthorizedPath("access denied");
        }
    };

}
