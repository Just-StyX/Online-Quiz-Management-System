package jsl.group.quiz.config;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import io.javalin.security.RouteRole;
import jsl.group.quiz.controllers.admin.AdminController;
import jsl.group.quiz.controllers.quiz.QuizController;
import jsl.group.quiz.controllers.user.UserController;
import jsl.group.quiz.utils.RenderStaticPages;
import jsl.group.quiz.utils.Role;

import java.util.Map;
import java.util.Set;

import static jsl.group.quiz.utils.RenderStaticPages.*;

public class JavalinConfiguration {
    public static Javalin javalin() {
        return Javalin.create(javalinConfig -> {
                    javalinConfig.fileRenderer(new JavalinJte(createTemplateEngine()));
                })
                .get("/", RenderStaticPages::renderHomePage, Role.ALL)

                // user registrations and login routes
                .get("/login", RenderStaticPages::renderLoginForm, Role.ALL)
                .get("/register", RenderStaticPages::renderRegistrationForm, Role.ALL)
                .post("/login", UserController.userLogin, Role.ALL)
                .post("/register", UserController.registerUser, Role.ALL)
                .get("/user", UserController.renderUserHome, Role.USERS, Role.ADMIN)

                // admin routes
                .get("/admin", UserController.renderUserHome, Role.ADMIN)
                .post("/admin/question", AdminController.createQuestion, Role.ADMIN)
                .get("/admin/question", AdminController.renderQuestionCreationForm, Role.ADMIN)
                .get("/admin/question/updates", AdminController.editQuestion, Role.ADMIN)
                .post("/admin/question/search", AdminController.searchQuestion, Role.ADMIN)
                .post("/admin/question/updates", AdminController.updateQuestion, Role.ADMIN)
                .get("/admin/question/remove", AdminController.deleteQuestionById, Role.ADMIN)

                // quiz routes
                .get("/quiz/question/quest", QuizController.getQuestionById, Role.ADMIN, Role.USERS)
                .get("/quiz/question/practice", QuizController.getPracticeQuestions, Role.USERS, Role.ADMIN)
                .get("/quiz/question/quiz", QuizController.quizQuestions, Role.USERS, Role.ADMIN)
                .get("/quiz/question/end", QuizController.endQuiz, Role.USERS, Role.ADMIN)

                // authorizations
                .before("/admin", context -> {
                    Map<String, Object> session = context.sessionAttributeMap();
                    Role role = (Role) session.get("role");
                    if (role != Role.ADMIN) {
                        context.render("404.jte");
                    }
                })
                .beforeMatched("/admin*", context -> {
                    Set<RouteRole> routeRoles = context.routeRoles();
                    if (routeRoles.contains(Role.USERS) || routeRoles.contains(Role.ALL)){
                        context.render("404.jte");
                    }
                })
                .beforeMatched("/user*", context -> {
                    Set<RouteRole> routeRoles = context.routeRoles();
                    if (routeRoles.contains(Role.ALL)) {
                        context.render("404.jte");
                    }
                })
                .beforeMatched("/quiz*", context -> {
                    Set<RouteRole> routeRoles = context.routeRoles();
                    if (routeRoles.contains(Role.ALL)) {
                        context.render("404.jte");
                    }
                });
    }
}
