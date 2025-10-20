package com.example.task01;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Logger {
    private final String name;
    private Level level;

    private static final Map<String, Logger> loggers = new HashMap<>();

    public enum Level {
        DEBUG,
        INFO,
        WARNING,
        ERROR
    }

    private Logger(String name) {
        this.name = name;
        this.level = Level.DEBUG;
    }

    public static Logger getLogger(String name) {
        return loggers.computeIfAbsent(name, Logger::new);
    }

    public String getName() {
        return name;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    private void logMessage(Level messageLevel, String message) {
        if (messageLevel.ordinal() >= level.ordinal()) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            String date = now.format(dateFormatter);
            String time = now.format(timeFormatter);

            System.out.println("[" + messageLevel + "] " + date + " " + time + " " + name + " - " + message);
        }
    }

    // Методы для уровня DEBUG
    public void debug(String message) {
        logMessage(Level.DEBUG, message);
    }

    public void debug(String template, Object... args) {
        logMessage(Level.DEBUG, MessageFormat.format(template, args));
    }

    // Методы для уровня INFO
    public void info(String message) {
        logMessage(Level.INFO, message);
    }

    public void info(String template, Object... args) {
        logMessage(Level.INFO, MessageFormat.format(template, args));
    }

    // Методы для уровня WARNING
    public void warning(String message) {
        logMessage(Level.WARNING, message);
    }

    public void warning(String template, Object... args) {
        logMessage(Level.WARNING, MessageFormat.format(template, args));
    }

    // Методы для уровня ERROR
    public void error(String message) {
        logMessage(Level.ERROR, message);
    }

    public void error(String template, Object... args) {
        logMessage(Level.ERROR, MessageFormat.format(template, args));
    }

    // Общие методы log
    public void log(Level level, String message) {
        logMessage(level, message);
    }

    public void log(Level level, String template, Object... args) {
        logMessage(level, MessageFormat.format(template, args));
    }
}