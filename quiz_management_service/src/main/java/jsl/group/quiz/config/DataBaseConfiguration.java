package jsl.group.quiz.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DataBaseConfiguration {
    private static final String databaseUrl = "jdbc:postgresql://localhost:5432/quiz";
    private static final String tables = """
            drop table if exists questions;
            drop table if exists users;
            create table questions (
                id serial primary key,
                question text not null,
                answer text not null,
                options text,
                subject varchar not null,
                points float not null,
                level varchar
            );
            
            create table users (
                id serial primary key,
                username varchar not null unique,
                password varchar not null,
                first_name varchar not null,
                middle_name varchar,
                last_name varchar not null,
                unique_id varchar unique not null,
                role char(5) not null
            );
            """;

    public static void createTables() throws SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(tables);
    }

    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("password", "podsam777");
        properties.setProperty("username", "jay7");
        return DriverManager.getConnection(databaseUrl, properties);
    }
}
