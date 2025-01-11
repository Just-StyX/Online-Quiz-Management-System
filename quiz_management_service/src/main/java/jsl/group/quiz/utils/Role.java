package jsl.group.quiz.utils;

import io.javalin.security.RouteRole;

public enum Role implements RouteRole {
    ALL("all"), USER("user"), ADMIN("admin");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
