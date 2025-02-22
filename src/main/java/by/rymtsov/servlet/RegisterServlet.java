package by.rymtsov.servlet;

import by.rymtsov.log.CustomLogger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import by.rymtsov.repository.UserRepository;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/registration")
public class RegisterServlet extends HttpServlet {

    UserRepository userRepository = new UserRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html;charset=UTF-8");

        String firstName = req.getParameter("firstName");
        String secondName = req.getParameter("secondName");
        int age = Integer.parseInt(req.getParameter("age"));
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            if (userRepository.addUser(firstName, secondName, age, login, password)) {
                resp.sendRedirect("/login.html");  // После успешной регистрации перенаправление на страницу входа
            } else {
                req.setAttribute("error", "Username already exists or invalid input.");
                req.getRequestDispatcher("/register.html").forward(req, resp);
            }
        } catch (ServletException | IOException | SQLException e) {
            CustomLogger.error("Error forwarding to register.html" + e.getMessage());
        }
    }
}
