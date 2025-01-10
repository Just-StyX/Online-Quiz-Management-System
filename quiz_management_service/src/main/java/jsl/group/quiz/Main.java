package jsl.group.quiz;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.rendering.template.JavalinJte;
import jsl.group.quiz.config.DataBaseConfiguration;
import jsl.group.quiz.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws SQLException {
        var app = Javalin.create(javalinConfig -> {
                    javalinConfig.fileRenderer(new JavalinJte());
                })
                .get("/", Main::renderHelloPage);
        app.start(8080);

        Connection connection = DataBaseConfiguration.getConnection();
        log.info("Connection details: {}", connection.getClientInfo());
    }

    private static void renderHelloPage(Context context) {
        User user = new User("Jay", "Welcome!");
        context.render("hello.jte", Collections.singletonMap("user", user));
    }
}