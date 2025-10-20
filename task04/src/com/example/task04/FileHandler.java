package com.example.task04;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Обработчик, выводящий сообщения в файл
 */
public class FileHandler implements MessageHandler {
    private final String filePath;

    public FileHandler(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void handle(String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(message);
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
        }
    }
}
