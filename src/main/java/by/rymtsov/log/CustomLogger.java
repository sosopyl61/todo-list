package by.rymtsov.log;

import by.rymtsov.service.PropsHandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class is a customer logger.
 */

public class CustomLogger {
    private static final String LOG_PATH = PropsHandler.getPropertyFromConfig("LOG_PATH");

    private static void log(String level, String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logMessage = String.format("%s [%s] %s", timestamp, level, message);

        System.out.println("LOG_PATH: " + LOG_PATH);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_PATH, true))) {
            writer.write(logMessage + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Error writing to file " + LOG_PATH + ": " + e.getMessage());
        }
    }

    public static void info(String message) {
        log("INFO", message);
    }

    public static void error(String message) {
        log("ERROR", message);
    }
}
