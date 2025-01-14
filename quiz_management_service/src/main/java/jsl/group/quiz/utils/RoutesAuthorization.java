package jsl.group.quiz.utils;

import io.javalin.http.Context;
import io.javalin.security.RouteRole;

import java.util.Map;
import java.util.Set;

public class RoutesAuthorization {
    public static boolean isAdmin(Context context) {
        RouteRole routeRole = context.routeRoles().stream().toList().getFirst();
        Map<String, Object> sessions = context.sessionAttributeMap();
        Role role = (Role) sessions.get("role");
        return (role == Role.ADMIN && role == routeRole);
    }

    public static boolean isUserOrAdmin(Context context) {
        Set<RouteRole> routeRoles = context.routeRoles();
        Map<String, Object> sessions = context.sessionAttributeMap();
        Role role = (Role) sessions.get("role");
        return ((role == Role.ADMIN || role == Role.USERS) && routeRoles.contains(role));
    }
}
