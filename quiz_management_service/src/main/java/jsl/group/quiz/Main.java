package jsl.group.quiz;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.rendering.template.JavalinJte;
import jsl.group.quiz.config.DataBaseConfiguration;
import jsl.group.quiz.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Collections;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static final boolean isDevSystem = true;
    public static void main(String[] args) throws SQLException {
        var app = Javalin.create(javalinConfig -> {
                    javalinConfig.fileRenderer(new JavalinJte(createTemplateEngine()));
                })
                .get("/", Main::renderHelloPage);
        app.start(8080);

        DataBaseConfiguration.createTables();

        log.info("Tables created successfully...");
    }

    private static void renderHelloPage(Context context) {
        User user = new User("Jay", "Welcome!");
        context.render("hello.jte", Collections.singletonMap("user", user));
    }

    private static TemplateEngine createTemplateEngine() {
        if (isDevSystem) {
            DirectoryCodeResolver codeResolver = new DirectoryCodeResolver(Path.of(  "quiz_management_service","src", "main","jte"));
            return TemplateEngine.create(codeResolver, ContentType.Html);
        } else {
            return TemplateEngine.createPrecompiled(Path.of("jte-classes"), ContentType.Html);
        }
    }
}