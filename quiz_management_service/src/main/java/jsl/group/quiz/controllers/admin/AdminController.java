package jsl.group.quiz.controllers.admin;

import io.javalin.http.Handler;
import jsl.group.quiz.models.Question;
import jsl.group.quiz.models.Quiz;
import jsl.group.quiz.services.admin_services.AdminServices;
import jsl.group.quiz.services.admin_services.AdminServicesImplementation;
import jsl.group.quiz.utils.exceptions.UnauthorizedPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static jsl.group.quiz.utils.RoutesAuthorization.isAdmin;
import static jsl.group.quiz.utils.RoutesAuthorization.isUserOrAdmin;

public class AdminController {
    private static final AdminServices adminServices = new AdminServicesImplementation();
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    public static Handler createQuestion = context -> {
        if (isAdmin(context)) {
            Map<String, List<String>> form = context.formParamMap();
            Map<Character, String> mapOptions = new TreeMap<>();
            String foundOptions = form.get("options").getFirst();
            if (!foundOptions.isBlank()) {
                Arrays.stream(foundOptions.split(";")).forEach(string -> {
                    String[] parts = string.split("=");
                    mapOptions.put(parts[0].charAt(0), parts[1]);
                });
            }
            Question question = new Question(
                    null,
                    form.get("question").getFirst(),
                    form.get("answer").getFirst(),
                    mapOptions,
                    form.get("subject").getFirst(),
                    Double.parseDouble(form.get("points").getFirst()),
                    form.get("level").getFirst()
            );
            String result = adminServices.createQuestion(question);
            context.json(result);
        } else {
            throw new UnauthorizedPath("access denied");
        }
    };

    public static Handler updateQuestion = context -> {
        if (isAdmin(context)) {
            String questionId = context.queryParam("id");
            Map<String, List<String>> form = context.formParamMap();
            Map<Character, String> mapOptions = new TreeMap<>();
            String foundOptions = form.get("options").getFirst();
            if (!foundOptions.isBlank()) {
                Arrays.stream(foundOptions.split(";")).forEach(string -> {
                    String[] parts = string.split("=");
                    mapOptions.put(parts[0].trim().charAt(0), parts[1].trim());
                });
            }
            Question question = new Question(
                    null,
                    form.get("question").getFirst().trim(),
                    form.get("answer").getFirst().trim(),
                    mapOptions,
                    form.get("subject").getFirst().trim(),
                    Double.parseDouble(form.get("points").getFirst().trim()),
                    form.get("level").getFirst().trim()
            );
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

    public static Handler editQuestion = context -> {
        if (isAdmin(context)) {
            Map<String, List<String>> parameters = context.queryParamMap();
            if (parameters.isEmpty()){
                Question question = new Question("", "", "", null, "", 0.0, "");
                context.render("edit_question_form.jte", Map.of("show", false, "question", question));
            } else {
                Map<Character, String> mapOptions = new TreeMap<>();
                String foundOptions = parameters.get("options").getFirst();
                if (!foundOptions.isBlank()) {
                    Arrays.stream(foundOptions.split(";")).forEach(string -> {
                        String[] parts = string.split("=");
                        mapOptions.put(parts[0].charAt(0), parts[1]);
                    });
                }
                Question question = new Question(
                        parameters.get("questionId").getFirst(),
                        parameters.get("question").getFirst(),
                        parameters.get("answer").getFirst(),
                        mapOptions,
                        parameters.get("subject").getFirst(),
                        Double.parseDouble(parameters.get("points").getFirst()),
                        parameters.get("level").getFirst()
                );
                context.render("edit_question_form.jte", Map.of("show", true, "question", question));
            }
        }
    };

    public static Handler searchQuestion = context -> {
        if (isAdmin(context)) {
            String searchQuestion = context.formParamMap().get("search-question").getFirst();
            Map<String, String> question = adminServices.findQuestionByQuestion(searchQuestion);
            String location = String.format("/admin/question/updates?questionId=%s&question=%s&answer=%s&options=%s&subject=%s&points=%s&level=%s&show=%s",
                    question.get("questionId"), question.get("question"), question.get("answer"), question.get("options"),
                    question.get("subject"), question.get("points"), question.get("level"), true);
            context.redirect(location);
        }
    };

}
