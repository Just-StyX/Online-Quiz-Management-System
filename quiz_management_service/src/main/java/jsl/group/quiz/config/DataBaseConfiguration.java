package jsl.group.quiz.config;

import jsl.group.quiz.Main;
import jsl.group.quiz.utils.EncryptDecrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.sql.*;
import java.util.Properties;

import static jsl.group.quiz.utils.PasswordProcesses.passwordEncryption;

public class DataBaseConfiguration {
    private static final Logger log = LoggerFactory.getLogger(DataBaseConfiguration.class);
    private static final String databaseUrl = "jdbc:postgresql://localhost:5432/quiz";
    private static final String tables = """
            create table if not exists questions (
                id serial primary key,
                question text not null,
                answer text not null,
                options text,
                subject varchar not null,
                points float not null,
                level varchar
            );
            
            create table if not exists users (
                id serial primary key,
                username varchar not null unique,
                password varchar not null,
                email varchar not null unique,
                first_name varchar not null,
                middle_name varchar,
                last_name varchar not null,
                unique_id varchar unique not null,
                role char(5) not null,
                key bytea not null
            );
            """;

    public static void createTables() throws Exception {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(tables);
        addAdmin(connection);
        connection.close();
        log.info("Tables created successfully...");
    }

    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("password", "");
        properties.setProperty("username", "");
        return DriverManager.getConnection(databaseUrl, properties);
    }

    private static void addAdmin(Connection connection) throws Exception {
        String query = "select * from users where role = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, "ADMIN");
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            SecretKey secretKey = EncryptDecrypt.generateKey();
            String addAdmin = """
                    insert into users (username, password, email, first_name, middle_name, last_name, unique_id, role, key) values(?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """;
            preparedStatement = connection.prepareStatement(addAdmin, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, "admin");
            preparedStatement.setString(2, passwordEncryption("admin", secretKey));
            preparedStatement.setString(3, "admin@email.com");
            preparedStatement.setString(4, "admin");
            preparedStatement.setString(5, "admin");
            preparedStatement.setString(6, "admin");
            preparedStatement.setString(7, "admin");
            preparedStatement.setString(8, "ADMIN");
            preparedStatement.setBytes(9, secretKey.getEncoded());
            preparedStatement.executeUpdate();
        }
    }
}
