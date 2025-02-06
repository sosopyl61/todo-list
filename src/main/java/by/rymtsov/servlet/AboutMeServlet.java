package by.rymtsov.servlet;

import by.rymtsov.log.CustomLogger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/about-me")
public class AboutMeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        CustomLogger.info("Servlet about me is working.");
        try {
            req.getRequestDispatcher("/WEB-INF/page/about-me.html").forward(req, resp);
        } catch (ServletException | IOException e) {
            CustomLogger.error(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        CustomLogger.info("Servlet about me is working.");
        try {
            req.getRequestDispatcher("/WEB-INF/page/about-me.html").forward(req, resp);
        } catch (ServletException | IOException e) {
            CustomLogger.error(e.getMessage());
        }
    }
}