package jsl.group.quiz.config;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import io.javalin.security.RouteRole;
import jsl.group.quiz.controllers.admin.AdminController;
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
                .get("/login", RenderStaticPages::renderLoginForm, Role.ALL)
                .get("/register", RenderStaticPages::renderRegistrationForm, Role.ALL)
                .get("/admin", UserController.renderAdminHome, Role.ADMIN)
                .get("/user", UserController.renderUserHome, Role.USERS, Role.ADMIN)
                .post("/login", UserController.userLogin, Role.ALL)
                .post("/register", UserController.registerUser, Role.ALL)
                .post("/admin/question", AdminController.createQuestion, Role.ADMIN)
                .put("/admin/question/updates", AdminController.updateQuestion, Role.ADMIN)
                .get("/admin/question/quest", AdminController.getQuestionById, Role.ADMIN)
                .delete("/admin/question/remove", AdminController.deleteQuestionById, Role.ADMIN)
                .get("/quiz/question/practice", AdminController.getPracticeQuestions, Role.USERS, Role.ADMIN)
                .get("/quiz/question/quiz", AdminController.quizQuestions, Role.USERS, Role.ADMIN)
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
