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
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    UserRepository userRepository;

    public LoginServlet() {
        this.userRepository = new UserRepository();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html;charset=UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            if (userRepository.isValid(username, password)) {
                HttpSession session = req.getSession();
                session.setAttribute("username", username);
                resp.sendRedirect("/todo");
            } else {
                req.getRequestDispatcher("/login.html").forward(req, resp);
            }
        } catch (IOException | ServletException e) {
            CustomLogger.error(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        CustomLogger.info("Login servlet is working.");
        try {
            String username = (String) req.getSession().getAttribute("username");
            if (username == null) {
                req.getRequestDispatcher("/login.html").forward(req, resp);
            } else {
                req.getRequestDispatcher("/todo").forward(req, resp);
            }
        } catch (ServletException | IOException e) {
            CustomLogger.error(e.getMessage());
        }
    }
}
