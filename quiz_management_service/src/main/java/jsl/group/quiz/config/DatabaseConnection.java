package jsl.group.quiz.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public enum DatabaseConnection {
    INSTANCE;

    private Connection connection;
    private DatabaseConnection() {
        try {
            Properties properties = new Properties();
            properties.setProperty("password", "");
            properties.setProperty("username", "");
            String databaseUrl = "jdbc:postgresql://localhost:5432/quiz";
            connection = DriverManager.getConnection(databaseUrl, properties);
        } catch (SQLException e) {
            Logger log = LoggerFactory.getLogger(DatabaseConnection.class);
            log.error(e.getMessage());
        }
    }
    public Connection getConnection() { return connection; }
}
