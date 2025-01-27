package jsl.group.quiz.config;

import jsl.group.quiz.singletons.DatabaseConnection;
import jsl.group.quiz.utils.EncryptDecrypt;
import jsl.group.quiz.utils.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.sql.*;

import static jsl.group.quiz.utils.PasswordProcesses.passwordEncryption;

public class DataBaseConfiguration {
    private static final Logger log = LoggerFactory.getLogger(DataBaseConfiguration.class);
    private static final String tables = """
            create table if not exists questions (
                question_id uuid primary key default gen_random_uuid(),
                question text not null unique,
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
        log.info("Tables created successfully...");
    }

    public static Connection getConnection() throws SQLException {
        return DatabaseConnection.INSTANCE.getConnection();
    }

    private static void addAdmin(Connection connection) throws Exception {
        String query = "select * from users where role = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, String.valueOf(Role.ADMIN));
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
            preparedStatement.setString(8, String.valueOf(Role.ADMIN));
            preparedStatement.setBytes(9, secretKey.getEncoded());
            preparedStatement.executeUpdate();
        }
    }
}
