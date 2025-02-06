package by.rymtsov.filter;

import by.rymtsov.log.CustomLogger;
import by.rymtsov.repository.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(value = {
        "/about-me",
        "/todo"
})
public class AuthFilter implements Filter {

    UserRepository userRepository;

    public AuthFilter() {
        userRepository = new UserRepository();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpSession session = servletRequest.getSession();
        String username = (String) session.getAttribute("username");

        try {
            if (username != null && userRepository.isContainsUserByUsername(username)) {
                chain.doFilter(request, response);
            } else {
                request.getRequestDispatcher("/login").forward(request, response);
            }
        } catch (IOException | ServletException e) {
            CustomLogger.error(e.getMessage());
        }
    }
}
