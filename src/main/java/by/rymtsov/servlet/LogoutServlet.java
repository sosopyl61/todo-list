package by.rymtsov.servlet;

import by.rymtsov.log.CustomLogger;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        CustomLogger.info("Logout servlet is working.");
        String username = (String) req.getSession().getAttribute("username");
        req.getSession().invalidate();
        System.out.println("User logged out: " + username);
        try {
            resp.sendRedirect(req.getContextPath() + "/login");
        } catch (IOException e) {
            CustomLogger.error(e.getMessage());
        }
    }
}