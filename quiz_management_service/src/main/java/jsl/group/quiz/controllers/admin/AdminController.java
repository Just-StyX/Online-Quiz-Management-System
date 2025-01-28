package jsl.group.quiz.controllers.admin;

import io.javalin.http.Handler;
import jsl.group.quiz.models.entities.Question;
import jsl.group.quiz.services.admin_services.AdminServices;
import jsl.group.quiz.services.admin_services.AdminServicesImplementation;
import jsl.group.quiz.utils.exceptions.UnauthorizedPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static jsl.group.quiz.utils.DataUtilities.emptyData;
import static jsl.group.quiz.utils.DataUtilities.getQuestionLocation;
import static jsl.group.quiz.utils.RoutesAuthorization.isAdmin;

public class AdminController {
    private static final AdminServices adminServices = AdminServicesImplementation.getInstance();
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
            String result = "Question created for " + adminServices.createQuestion(question);
            String location = String.format("/admin/question?created=%s", result);
            context.redirect(location);
        } else {
            throw new UnauthorizedPath("access denied");
        }
    };

    public static Handler renderQuestionCreationForm = context -> {
        if (isAdmin(context)) {
            String created = context.queryParam("created");
            if (created != null && !created.isBlank()) {
                context.render("question_form.jte", Map.of("createdBool", true, "created", created));
            } else {
                context.render("question_form.jte", Map.of("createdBool", false, "created", ""));
            }
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
            Map<String, String> emptyData = emptyData();
            String location = getQuestionLocation(emptyData, false, result, true);
            context.redirect(location);
        } else {
            throw new UnauthorizedPath("access denied");
        }
    };



    public static Handler deleteQuestionById = context -> {
        if (isAdmin(context)) {
            String questionId = context.queryParam("id");
            String result = adminServices.deleteQuestionById(questionId);
            Map<String, String> emptyData = emptyData();
            String location = getQuestionLocation(emptyData, false, result, true);
            context.redirect(location);
        } else {
            throw new UnauthorizedPath("access denied");
        }
    };


    public static Handler editQuestion = context -> {
        if (isAdmin(context)) {
            Map<String, List<String>> parameters = context.queryParamMap();
            if (parameters.isEmpty()){
                Question question = new Question("", "", "", null, "", 0.0, "");
                context.render("edit_question_form.jte", Map.of("show", false, "question", question,
                        "message", " ", "messageBool", false));
            } else {
                Map<Character, String> mapOptions = new TreeMap<>();
                String foundOptions = parameters.get("options").getFirst();
                if (!foundOptions.isBlank()) {
                    Arrays.stream(foundOptions.split(";")).forEach(string -> {
                        String[] parts = string.split("=");
                        mapOptions.put(parts[0].charAt(0), parts[1]);
                    });
                }
                boolean messageBool = Boolean.parseBoolean(parameters.get("messageBool").getFirst());
                String message = parameters.get("message").getFirst();
                Question question = new Question(
                        parameters.get("questionId").getFirst(),
                        parameters.get("question").getFirst(),
                        parameters.get("answer").getFirst(),
                        mapOptions,
                        parameters.get("subject").getFirst(),
                        Double.parseDouble(parameters.get("points").getFirst()),
                        parameters.get("level").getFirst()
                );
                if (messageBool) {
                    context.render("edit_question_form.jte", Map.of("show", false, "question", question,
                            "message", message, "messageBool", true));
                } else {
                    context.render("edit_question_form.jte", Map.of("show", true, "question", question,
                            "message", " ", "messageBool", false));
                }
            }
        }
    };

    public static Handler searchQuestion = context -> {
        if (isAdmin(context)) {
            String searchQuestion = context.formParamMap().get("search-question").getFirst();
            Map<String, String> question = adminServices.findQuestionByQuestion(searchQuestion);
            String location = getQuestionLocation(question, true, "", false);
            context.redirect(location);
        }
    };

}
