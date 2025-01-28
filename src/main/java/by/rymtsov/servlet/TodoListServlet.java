package by.rymtsov.servlet;

import by.rymtsov.log.CustomLogger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import by.rymtsov.repository.TaskRepository;

import java.io.IOException;
import java.util.Set;

@WebServlet("/todo")
public class TodoListServlet extends HttpServlet {

    private final TaskRepository taskRepository;

    public TodoListServlet() {
        this.taskRepository = new TaskRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        CustomLogger.info("Todo list servlet is working.");
        String username = (String) req.getSession().getAttribute("username");
        req.setAttribute("username", username);

        Set<String> tasks = taskRepository.getTasksByUsername(username);

        req.setAttribute("tasks", tasks);
        try {
            req.getRequestDispatcher("/page/todo-list.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            CustomLogger.error(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        CustomLogger.info("Todo list servlet is working.");
        String task = req.getParameter("task");
        String deletedTask = req.getParameter("deletedTask");

        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");

        Set<String> tasks = taskRepository.getTasksByUsername(username);

        if (task != null && !task.isEmpty()) {
            tasks.add(task);
            CustomLogger.info("Task added: " + task);
        } else if (deletedTask != null && !deletedTask.isEmpty()) {
            tasks.remove(deletedTask);
            CustomLogger.info("Task deleted: " + deletedTask);
        }
        taskRepository.getTaskList().put(username, tasks);

        req.getSession().setAttribute("tasks", tasks);

        try {
            req.getRequestDispatcher("/page/todo-list.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            CustomLogger.error(e.getMessage());
        }
    }
}
