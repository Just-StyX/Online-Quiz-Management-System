package jsl.group.quiz.utils;

import gg.jte.CodeResolver;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;
import io.javalin.http.Context;

import java.nio.file.Path;

public class RenderStaticPages {
    private static final boolean isDevSystem = true;
    public static void renderHomePage(Context context) {
        context.render("home.jte");
    }
    public static void renderRegistrationForm(Context context) { context.render("registration_form.jte"); }
    public static void renderLoginForm(Context context) { context.render("login.jte"); }

    public static TemplateEngine createTemplateEngine() {
        if (isDevSystem) {
            CodeResolver codeResolver = new DirectoryCodeResolver(Path.of(  "src", "main","jte"));
            return TemplateEngine.create(codeResolver, ContentType.Html);
        } else {
            return TemplateEngine.createPrecompiled(Path.of("jte-classes"), ContentType.Html);
        }
    }
}
