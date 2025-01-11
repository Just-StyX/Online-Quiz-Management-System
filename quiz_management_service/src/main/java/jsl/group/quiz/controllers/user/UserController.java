package jsl.group.quiz.controllers.user;

import io.javalin.http.Handler;
import io.javalin.security.RouteRole;
import jsl.group.quiz.models.FoundUser;
import jsl.group.quiz.models.UserLogin;
import jsl.group.quiz.models.UserRegistration;
import jsl.group.quiz.services.user_services.UserServices;
import jsl.group.quiz.services.user_services.UserServicesImplementation;
import jsl.group.quiz.utils.Role;
import jsl.group.quiz.utils.exceptions.UnauthorizedPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class UserController {
    private static final UserServices userServices = new UserServicesImplementation();
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public static Handler registerUser = context -> {
        Map<String, List<String>> form = context.formParamMap();
        UserRegistration userRegistration = new UserRegistration(
                form.get("username").getFirst(),
                form.get("password").getFirst(),
                form.get("email").getFirst(),
                form.get("firstName").getFirst(),
                form.get("middleName").getFirst(),
                form.get("lastName").getFirst()
        );
        String results = userServices.userRegistration(userRegistration);
        if (results != null) context.redirect("/login");
        else context.status(400);
    };

    public static Handler userLogin = context -> {
        Map<String, List<String>> form = context.formParamMap();
        UserLogin login = new UserLogin(
                form.get("email").getFirst(),
                form.get("password").getFirst()
        );
        FoundUser foundUser = userServices.userLogin(login);
        String location = String.format("/user?username=%s&firstName=%s", foundUser.username(), foundUser.firstName());
        context.sessionAttribute("role", foundUser.role());
        context.redirect(location);
    };

    public static Handler renderUserHome = context -> {
        Map<String, List<String>> queryParameters = context.queryParamMap();
        Map<String, Object> sessions = context.sessionAttributeMap();
        Role role = (Role) sessions.get("role");
        Set<RouteRole> routeRoles = context.routeRoles();
        if (routeRoles.contains(role)) {
            FoundUser foundUser = new FoundUser(queryParameters.get("username").getFirst(),
                    queryParameters.get("firstName").getFirst(), role);
            context.render("user.jte", Collections.singletonMap("foundUser", foundUser));
        } else {
            throw new UnauthorizedPath("authentication failure");
        }
    };
}
