package by.rymtsov.repository;

import by.rymtsov.log.CustomLogger;
import by.rymtsov.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserRepository {
    private static Map<String, String> users = new HashMap<>();
    public DatabaseService databaseService;

    public UserRepository() {
        databaseService = new DatabaseService();
    }

    public Set<User> getAllUsers() {
        Set<User> users = new HashSet<>();
        Connection connection = databaseService.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SQLQuerry.GET_ALL_USERS);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                users.add(parseUser(result));
            }
        } catch (SQLException e) {
            CustomLogger.error(e.getMessage());
        }
        return users;
    }

    public Boolean isValid(String username, String password) {
        User userFromDatabase = null;
        Connection connection = databaseService.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SQLQuerry.IS_VALID);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                userFromDatabase = parseUser(result);
            }
        } catch (SQLException e) {
            CustomLogger.error(e.getMessage());
        }

        if (userFromDatabase == null || userFromDatabase.getUsername() == null || userFromDatabase.getUserPassword() == null) {
            return false;
        }
        if (userFromDatabase.getUsername().equals(username)) {
            return userFromDatabase.getUserPassword().equals(password);
        }
        return false;
    }

    public Boolean isContainsUserByUsername(String username) {
        Set<User> allUsers = getAllUsers();
        for (User user : allUsers) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static boolean addUser(String username, String password) {
        if (username == null || password == null || users.containsKey(username)) {
            return false;
        } else {
            users.put(username, password);
            CustomLogger.info("Adding user " + username);
            return true;
        }
    }

    public User parseUser(ResultSet result) throws SQLException {
        User user = new User();
        user.setId(result.getLong("id"));
        user.setUsername(result.getString("username"));
        user.setUserPassword(result.getString("user_password"));
        user.setCreated(result.getDate("created"));
        user.setChanged(result.getDate("changed"));
        return user;
    }
}
