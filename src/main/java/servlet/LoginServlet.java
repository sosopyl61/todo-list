package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import repository.UserRepository;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        boolean isUserValid = UserRepository.isValid(username, password);

        if (isUserValid) {
            HttpSession session = req.getSession();
            session.setAttribute("username", username);
            resp.sendRedirect("/todo");
        } else {
            req.getRequestDispatcher("/login.html").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = (String) req.getSession().getAttribute("username");
        if (username == null) {
            req.getRequestDispatcher("/login.html").forward(req, resp);
        } else {
            req.getRequestDispatcher("/todo").forward(req, resp);
        }
    }
}
