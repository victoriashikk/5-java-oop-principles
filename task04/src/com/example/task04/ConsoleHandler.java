package com.example.task04;

/**
 * Обработчик, выводящий сообщения в консоль
 */
public class ConsoleHandler implements MessageHandler {
    @Override
    public void handle(String message) {
        System.out.println(message);
    }
}