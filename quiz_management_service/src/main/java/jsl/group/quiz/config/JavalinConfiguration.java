package jsl.group.quiz.config;

import gg.jte.CodeResolver;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.TemplateOutput;
import gg.jte.output.StringOutput;
import gg.jte.resolve.DirectoryCodeResolver;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.rendering.template.JavalinJte;
import io.javalin.security.Roles;
import io.javalin.security.RouteRole;
import jsl.group.quiz.controllers.user.UserController;
import jsl.group.quiz.models.UserLogin;
import jsl.group.quiz.utils.Role;
import jsl.group.quiz.utils.exceptions.UnauthorizedPath;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class JavalinConfiguration {
    private static final boolean isDevSystem = true;
    private static void renderHomePage(Context context) {
        context.render("home.jte");
    }
    private static void renderRegistrationForm(Context context) { context.render("registration_form.jte"); }
    private static void renderLoginForm(Context context) { context.render("login.jte"); }

    private static TemplateEngine createTemplateEngine() {
        if (isDevSystem) {
            CodeResolver codeResolver = new DirectoryCodeResolver(Path.of(  "src", "main","jte"));
            return TemplateEngine.create(codeResolver, ContentType.Html);
        } else {
            return TemplateEngine.createPrecompiled(Path.of("jte-classes"), ContentType.Html);
        }
    }

    public static Javalin javalin() {
        return Javalin.create(javalinConfig -> {
                    javalinConfig.fileRenderer(new JavalinJte(createTemplateEngine()));
                })
                .get("/", JavalinConfiguration::renderHomePage, Role.ALL)
                .get("/login", JavalinConfiguration::renderLoginForm, Role.ALL)
                .get("/user", UserController.renderUserHome, Role.USER, Role.ADMIN)
                .get("/register", JavalinConfiguration::renderRegistrationForm, Role.ALL)
                .post("/login", UserController.userLogin, Role.ALL)
                .post("/register", UserController.registerUser, Role.ALL)
                .beforeMatched("/admin*", context -> {
                    Set<RouteRole> routeRoles = context.routeRoles();
                    if (routeRoles.contains(Role.USER) || routeRoles.contains(Role.ALL)){
                        throw new UnauthorizedPath("access denied");
                    }
                })
                .beforeMatched("/user*", context -> {
                    Set<RouteRole> routeRoles = context.routeRoles();
                    if (routeRoles.contains(Role.ALL)) {
                        throw new UnauthorizedPath("access denied");
                    }
                });
    }
}
