package by.rymtsov.service;

import by.rymtsov.log.CustomLogger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropsHandler {
    private static final String CONFIG_FILE_PATH = "config.properties";
    private static Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = PropsHandler.class.getClassLoader().getResourceAsStream(CONFIG_FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            CustomLogger.error("Failed to load properties from " + CONFIG_FILE_PATH + ": " + e.getMessage());
        }
    }

    public static String getPropertyFromConfig(String propName) {
        String value = properties.getProperty(propName);
        return value;
    }
}
