package by.rymtsov.repository;

import by.rymtsov.log.CustomLogger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseService {
    final String URL = "jdbc:postgresql://localhost:5432/database-32";
    final String DB_USER = "user32";
    final String DB_PASSWORD = "qwerty";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            CustomLogger.error(e.getMessage());
        }
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            CustomLogger.error(e.getMessage());
        }
        return null;
    }
}
