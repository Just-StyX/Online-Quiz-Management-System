package jsl.group.quiz.services.user_services;

import jsl.group.quiz.config.DataBaseConfiguration;
import jsl.group.quiz.models.entities.FoundUser;
import jsl.group.quiz.models.entities.UserLogin;
import jsl.group.quiz.models.entities.UserRegistration;
import jsl.group.quiz.utils.EncryptDecrypt;
import jsl.group.quiz.utils.Role;
import jsl.group.quiz.utils.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.sql.*;

import static jsl.group.quiz.utils.PasswordProcesses.passwordDecryption;
import static jsl.group.quiz.utils.PasswordProcesses.passwordEncryption;

public class UserRegistrationServiceImplementation implements UserRegistrationServices {
    private static final Logger log = LoggerFactory.getLogger(UserRegistrationServiceImplementation.class);
    @Override
    public String register(UserRegistration userRegistration) {
        try {
            Connection connection = DataBaseConfiguration.getConnection();
            SecretKey secretKey = EncryptDecrypt.generateKey();
            String uniqueId = userRegistration.email().split("@")[0];
            String query = """
                    insert into users (username, password, email, first_name, middle_name, last_name, unique_id, role, key) values(?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, userRegistration.username());
            preparedStatement.setString(2, passwordEncryption(userRegistration.password(), secretKey));
            preparedStatement.setString(3, userRegistration.email());
            preparedStatement.setString(4, userRegistration.firstName());
            preparedStatement.setString(5, userRegistration.middleName());
            preparedStatement.setString(6, userRegistration.lastName());
            preparedStatement.setString(7, uniqueId);
            preparedStatement.setString(8, String.valueOf(Role.USERS));
            preparedStatement.setBytes(9, secretKey.getEncoded());
            preparedStatement.executeUpdate();

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(!pkeyResultSet.wasNull()){
                return "Registration complete. Continue to login";
            } else {
                return "Registration not completed";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return "";
    }

    @Override
    public FoundUser login(UserLogin userLogin) {
        try {
            Connection connection = DataBaseConfiguration.getConnection();
            String query = """
                    select * from users where email = ?
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userLogin.email());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                byte[] bytes = resultSet.getBytes("key");
                SecretKey foundSecreteKey = new SecretKeySpec(bytes, "AES");
                String encryptedPassword = resultSet.getString("password");
                String decodedPassword = passwordDecryption(encryptedPassword, foundSecreteKey);
                if (!decodedPassword.equals(userLogin.password())) {
                    throw new UserNotFoundException("Invalid username or password");
                }
                String username = resultSet.getString("username");
                String firstName = resultSet.getString("first_name");
                Role role = Role.valueOf(resultSet.getString("role"));
                return new FoundUser(username, firstName, role);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean nonUniqueness(String username, String email) throws SQLException {
        try {
            Connection connection = DataBaseConfiguration.getConnection();
            String query = "select username, email from users where username = ? or email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

}
