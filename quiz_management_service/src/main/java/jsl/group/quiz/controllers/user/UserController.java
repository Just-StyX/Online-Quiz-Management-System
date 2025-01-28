package jsl.group.quiz.controllers.user;

import io.javalin.http.Handler;
import io.javalin.security.RouteRole;
import jsl.group.quiz.models.entities.FoundUser;
import jsl.group.quiz.models.entities.UserLogin;
import jsl.group.quiz.models.entities.UserRegistration;
import jsl.group.quiz.services.user_services.UserServices;
import jsl.group.quiz.services.user_services.UserServicesImplementation;
import jsl.group.quiz.utils.Role;
import jsl.group.quiz.utils.exceptions.UnauthorizedPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class UserController {
    private static final UserServices userServices = UserServicesImplementation.getInstance();
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
        UserLogin login = new UserLogin(form.get("email").getFirst(), form.get("password").getFirst());
        FoundUser foundUser = userServices.userLogin(login);
        Role role = foundUser.role();
        String location = "";
        if (role == Role.USERS) {
            location = "/user";
        }
        if (role == Role.ADMIN) {
            location = "/admin";
        }
        context.sessionAttribute("role", role);
        context.sessionAttribute("username", foundUser.username());
        context.sessionAttribute("firstName", foundUser.firstName());
        context.redirect(location);
    };

    public static Handler renderUserHome = context -> {
        Map<String, Object> sessions = context.sessionAttributeMap();
        Role role = (Role) sessions.get("role");
        String firstName = (String) sessions.get("firstName");
        String username = (String) sessions.get("username");
        Set<RouteRole> routeRoles = context.routeRoles();
        FoundUser foundUser = new FoundUser(username, firstName, role);
        if (routeRoles.contains(role) && role == Role.USERS) {
            context.render("user.jte", Collections.singletonMap("foundUser", foundUser));
        } else if (routeRoles.contains(role) && role == Role.ADMIN) {
            context.render("admin.jte", Collections.singletonMap("foundUser", foundUser));
        } else {
            throw new UnauthorizedPath("authentication failure");
        }
    };
}
