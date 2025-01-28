package by.rymtsov.servlet;

import by.rymtsov.log.CustomLogger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import by.rymtsov.repository.UserRepository;

import java.io.IOException;

@WebServlet("/registration")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html;charset=UTF-8");

        CustomLogger.info("Registration servlet is working.");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirm-password");

        boolean isUserAdded = UserRepository.addUser(username, password);

        try {
            if (isUserAdded && confirmPassword.equals(password)) {
                HttpSession session = req.getSession();
                session.setAttribute("username", username);
                req.getRequestDispatcher("/login.html").forward(req, resp);
            } else {
                req.getRequestDispatcher("/registration.html").forward(req, resp);
            }
        } catch (ServletException | IOException e) {
            CustomLogger.error(e.getMessage());
        }
    }
}
