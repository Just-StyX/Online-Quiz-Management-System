package jsl.group.quiz.config;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;
import io.javalin.http.Context;
import jsl.group.quiz.models.UserLogin;

import java.nio.file.Path;
import java.util.Collections;

public class JavalinConfiguration {
    private static final boolean isDevSystem = true;
    public static void renderHelloPage(Context context) {
        UserLogin user = new UserLogin("Jay", "Welcome!");
        context.render("hello.jte", Collections.singletonMap("user", user));
    }

    public static TemplateEngine createTemplateEngine() {
        if (isDevSystem) {
            DirectoryCodeResolver codeResolver = new DirectoryCodeResolver(Path.of(  "quiz_management_service","src", "main","jte"));
            return TemplateEngine.create(codeResolver, ContentType.Html);
        } else {
            return TemplateEngine.createPrecompiled(Path.of("jte-classes"), ContentType.Html);
        }
    }
}
