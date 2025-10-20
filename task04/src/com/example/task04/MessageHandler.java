package com.example.task04;

/**
 * Обработчик сообщений логгера
 */
public interface MessageHandler {
    /**
     * Обрабатывает сообщение
     * @param message сообщение для обработки
     */
    void handle(String message);
}