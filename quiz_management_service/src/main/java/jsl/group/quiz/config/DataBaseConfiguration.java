package jsl.group.quiz.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConfiguration {
    private static final String databaseUrl = "jdbc:postgresql://localhost:5432/shell";

    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("password", "");
        properties.setProperty("username", "");
        return DriverManager.getConnection(databaseUrl, properties);
    }
}
