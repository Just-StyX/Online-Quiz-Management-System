package jsl.group.quiz.utils;

import io.javalin.security.RouteRole;

public enum Role implements RouteRole {
    ALL, USERS, ADMIN
}
