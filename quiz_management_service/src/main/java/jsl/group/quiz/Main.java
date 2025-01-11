package jsl.group.quiz;

import io.javalin.Javalin;
import jsl.group.quiz.config.DataBaseConfiguration;
import jsl.group.quiz.config.JavalinConfiguration;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws Exception {
        Javalin app = JavalinConfiguration.javalin();
        app.start(8080);

        DataBaseConfiguration.createTables();
    }
}