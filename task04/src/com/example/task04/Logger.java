package com.example.task04;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Продвинутый логгер с поддержкой обработчиков
 */
public class Logger {
    private final String name;
    private Level level;
    private final List<MessageHandler> handlers = new ArrayList<>();

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

    // Singleton для логгеров по имени
    private static final java.util.Map<String, Logger> loggers = new java.util.HashMap<>();

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

    /**
     * Добавляет обработчик сообщений
     * @param handler обработчик
     */
    public void addHandler(MessageHandler handler) {
        handlers.add(handler);
    }

    /**
     * Удаляет обработчик сообщений
     * @param handler обработчик
     */
    public void removeHandler(MessageHandler handler) {
        handlers.remove(handler);
    }

    private void logMessage(Level messageLevel, String message) {
        if (messageLevel.ordinal() >= level.ordinal()) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            String date = now.format(dateFormatter);
            String time = now.format(timeFormatter);

            String formattedMessage = String.format("[%s] %s %s %s - %s",
                    messageLevel, date, time, name, message);

            // Отправляем сообщение всем обработчикам
            for (MessageHandler handler : handlers) {
                handler.handle(formattedMessage);
            }
        }
    }

    // Методы для уровней логирования
    public void debug(String message) {
        logMessage(Level.DEBUG, message);
    }

    public void debug(String template, Object... args) {
        logMessage(Level.DEBUG, String.format(template, args));
    }

    public void info(String message) {
        logMessage(Level.INFO, message);
    }

    public void info(String template, Object... args) {
        logMessage(Level.INFO, String.format(template, args));
    }

    public void warning(String message) {
        logMessage(Level.WARNING, message);
    }

    public void warning(String template, Object... args) {
        logMessage(Level.WARNING, String.format(template, args));
    }

    public void error(String message) {
        logMessage(Level.ERROR, message);
    }

    public void error(String template, Object... args) {
        logMessage(Level.ERROR, String.format(template, args));
    }

    public void log(Level level, String message) {
        logMessage(level, message);
    }

    public void log(Level level, String template, Object... args) {
        logMessage(level, String.format(template, args));
    }
}