package jsl.group.quiz;

import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinJte;
import jsl.group.quiz.config.DataBaseConfiguration;
import jsl.group.quiz.config.JavalinConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws SQLException {
        var app = Javalin.create(javalinConfig -> {
                    javalinConfig.fileRenderer(new JavalinJte(JavalinConfiguration.createTemplateEngine()));
                })
                .get("/", context -> JavalinConfiguration.renderHelloPage(context));
        app.start(8080);

        DataBaseConfiguration.createTables();

        log.info("Tables created successfully...");
    }


}