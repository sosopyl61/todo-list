package by.rymtsov.repository;

import by.rymtsov.log.CustomLogger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TaskRepository {
    private final Map<String, Set<String>> taskList;

    public TaskRepository() {
        taskList = new HashMap<>();
    }

    public Map<String, Set<String>> getTaskList() {
        return taskList;
    }

    public Set<String> getTasksByUsername(String taskName) {
        CustomLogger.info("Getting tasks by username: " + taskName);
        return taskList.get(taskName) == null ? new HashSet<>() : taskList.get(taskName);
    }
}