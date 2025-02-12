package by.rymtsov.repository;

public interface SQLQuery {
    String GET_ALL_USERS = "SELECT * FROM users";
    String GET_USER_BY_ID = "SELECT * FROM users WHERE id=?";
    String DELETE_USER_BY_ID = "DELETE FROM users WHERE id=?";
    String IS_VALID = "SELECT * FROM users WHERE username = ? AND user_password = ?";
    String INSERT_USER = "INSERT INTO users (username, user_password, created, changed) VALUES (?, ?, NOW(), NOW())";
    String CREATE_USER = "INSERT INTO users (id, firstname, secondname, created, changed, age) VALUES (DEFAULT, ?, ?, ?, ?, ?)";
    String CREATE_SECURITY = "INSERT INTO security (id, login, password, user_id) VALUES (DEFAULT, ?, ?, ?)";
}
