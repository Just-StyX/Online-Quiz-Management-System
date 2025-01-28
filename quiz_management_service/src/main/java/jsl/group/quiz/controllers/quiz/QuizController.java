package jsl.group.quiz.controllers.quiz;

import io.javalin.http.Handler;
import jsl.group.quiz.models.entities.FoundUser;
import jsl.group.quiz.models.entities.Question;
import jsl.group.quiz.models.entities.Quiz;
import jsl.group.quiz.models.entities.QuizQuestion;
import jsl.group.quiz.services.quiz_services.QuizServiceImplementation;
import jsl.group.quiz.services.quiz_services.QuizServices;
import jsl.group.quiz.singletons.DataCachedCenter;
import jsl.group.quiz.utils.Role;
import jsl.group.quiz.utils.exceptions.UnauthorizedPath;
import jsl.group.quiz.utils.structures.Position;
import jsl.group.quiz.utils.structures.PositionalLinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jsl.group.quiz.utils.RoutesAuthorization.isUserOrAdmin;

public class QuizController {
    private static final QuizServices quizServices = QuizServiceImplementation.getInstance();
    private static final Logger log = LoggerFactory.getLogger(QuizController.class);

    public static Handler getQuestionById = context -> {
        if (isUserOrAdmin(context)) {
            String questionId = context.queryParam("id");
            Question question = quizServices.findQuestionById(questionId);
            context.json(question);
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
            Quiz practiceQuestions = quizServices.findQuestions(subject, level, limit);
            context.json(practiceQuestions);
        } else {
            throw new UnauthorizedPath("access denied");
        }
    };

    public static Handler endQuiz = context -> {
        if (isUserOrAdmin(context)) {
            Map<String, Object> sessions = context.sessionAttributeMap();
            String subject = (String) sessions.get("subject");
            String username = (String) sessions.get("username");
            String firstName = (String) sessions.get("firstName");
            Role role = (Role) sessions.get("role");
            FoundUser foundUser = new FoundUser(username, firstName, role);
            List<String> results = quizServices.endQuiz(username, subject);
            context.render("user.jte", Map.of("foundUser", foundUser, "result", results));
        }
    };

    public static Handler quizQuestions = context -> {
        if (isUserOrAdmin(context)) {
            Map<String, Object> sessions = context.sessionAttributeMap();
            String username = (String) sessions.get("username");
            Map<String, List<String>> parameters = context.queryParamMap();

            if ((parameters.containsKey("subject") && parameters.containsKey("limit"))) {
                String subject = parameters.get("subject").getFirst();
                int limit = Integer.parseInt(parameters.get("limit").getFirst());
                PositionalLinkedList<QuizQuestion> questions = quizServices.quizQuestions(subject, limit, username);
                context.sessionAttribute("subject", subject);
                context.render("quiz.jte", Map.of("question", questions.first().getElement(), "first", true, "last", false));
            } else {
                Map<String, Map<String, Character>> userAnswers = DataCachedCenter.INSTANCE.getDataCenter().userAnswerChoices;
                PositionalLinkedList<QuizQuestion> innerQuestions = DataCachedCenter.INSTANCE.getDataCenter().quizQuestions.get(username);

                if (parameters.containsKey("id") && parameters.containsKey("choice")) {
                    String questionId = parameters.get("id").getFirst();
                    Character answerChoice = parameters.get("choice").getFirst().charAt(0);
                    boolean first = Boolean.parseBoolean(parameters.get("first").getFirst());
                    boolean last = Boolean.parseBoolean(parameters.get("last").getFirst());
                    QuizQuestion quizQuestion = new QuizQuestion("", null, 0, "");
                    for (Position<QuizQuestion> position: innerQuestions) {
                        if (position.getElement().questionId().equals(questionId)) quizQuestion = position.getElement();
                    }
                    Map<String, Character> foundUser = userAnswers.compute(username, (k, v) -> (v == null) ? new HashMap<>() : v);
                    foundUser.put(questionId, answerChoice);
                    userAnswers.put(username, foundUser);
                    context.render("quiz.jte", Map.of("question", quizQuestion, "first", first, "last", last));
                }

                if (parameters.containsKey("next")) {
                    QuizQuestion quizQuestion = innerQuestions.next();
                    context.render("quiz.jte", Map.of("question", quizQuestion,
                            "first", false, "last", quizQuestion.equals(innerQuestions.last().getElement())));
                }

                if (parameters.containsKey("previous")) {
                    QuizQuestion quizQuestion = innerQuestions.previous();
                    context.render("quiz.jte", Map.of("question", quizQuestion,
                            "first", quizQuestion.equals(innerQuestions.first().getElement()), "last", false));
                }
            }
        } else {
            throw new UnauthorizedPath("access denied");
        }
    };
}
