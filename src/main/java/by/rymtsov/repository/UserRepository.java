package by.rymtsov.repository;

import by.rymtsov.log.CustomLogger;
import by.rymtsov.model.User;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class UserRepository {
    public DatabaseService databaseService;

    public UserRepository() {
        databaseService = new DatabaseService();
    }

    public Set<User> getAllUsers() {
        Set<User> users = new HashSet<>();
        try (Connection connection = databaseService.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.GET_ALL_USERS);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                users.add(parseUser(result));
            }
        }
        catch (SQLException e) {
            CustomLogger.error("Error getting users: " + e.getMessage());
        }
        return users;
    }

    public Boolean isValid(String username, String password) {
        try (Connection connection = databaseService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQLQuery.IS_VALID);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();
            return result.next();
        } catch (SQLException e) {
            CustomLogger.error(e.getMessage());
        }
        return false;
    }

    public Boolean isContainsUserByUsername(String login) {
        try (Connection connection = databaseService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQLQuery.GET_SECURITY_BY_LOGIN);
            statement.setString(1, login);
            ResultSet result = statement.executeQuery();
            return result.next();
        } catch (SQLException e) {
            CustomLogger.error(e.getMessage());
        }
        return false;
    }

    public User parseUser(ResultSet result) {
        try {
            User user = new User();
            user.setId(result.getLong("id"));
            user.setFirstname(result.getString("firstname"));
            user.setSecondname(result.getString("secondname"));
            user.setAge(result.getInt("age"));
            user.setCreated(result.getDate("created"));
            user.setChanged(result.getDate("changed"));
            return user;
        } catch (SQLException e) {
            CustomLogger.error("Error parsing user: " + e.getMessage());
        }
        return null;
    }

    public Boolean addUser(String firstName, String secondName, int age, String login, String password) throws SQLException {
        Connection connection = databaseService.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement createUserStatement = connection.prepareStatement(SQLQuery.CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            createUserStatement.setString(1, firstName);
            createUserStatement.setString(2, secondName);
            createUserStatement.setDate(3, new Date(System.currentTimeMillis()));
            createUserStatement.setDate(4, new Date(System.currentTimeMillis()));
            createUserStatement.setInt(5, age);
            createUserStatement.executeUpdate();

            ResultSet generatedKeys = createUserStatement.getGeneratedKeys();
            long userID = -1;
            if (generatedKeys.next()) {
                userID = generatedKeys.getLong(1);
            }

            PreparedStatement createUserSecurity = connection.prepareStatement(SQLQuery.CREATE_SECURITY);
            createUserSecurity.setString(1, login);
            createUserSecurity.setString(2, password);
            createUserSecurity.setLong(3, userID);
            createUserSecurity.executeUpdate();

            connection.commit();
            return true;
        } catch (SQLException e) {
            CustomLogger.error("Error adding user: " + e.getMessage());
            connection.rollback();
        }
        return false;
    }
}
