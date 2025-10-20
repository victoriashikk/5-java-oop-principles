package com.example.task04;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Обработчик с ротацией файлов
 */
public class RotationFileHandler implements MessageHandler {
    private final String basePath;
    private final ChronoUnit rotationUnit;
    private LocalDateTime currentRotationTime;
    private String currentFilePath;
    private PrintWriter currentWriter;

    public RotationFileHandler(String basePath, ChronoUnit rotationUnit) {
        this.basePath = basePath;
        this.rotationUnit = rotationUnit;
        this.currentRotationTime = getCurrentRotationTime();
        this.currentFilePath = generateFilePath();
    }

    private LocalDateTime getCurrentRotationTime() {
        LocalDateTime now = LocalDateTime.now();
        switch (rotationUnit) {
            case HOURS:
                return now.truncatedTo(ChronoUnit.HOURS);
            case DAYS:
                return now.truncatedTo(ChronoUnit.DAYS);
            case MINUTES:
                return now.truncatedTo(ChronoUnit.MINUTES);
            default:
                return now.truncatedTo(ChronoUnit.HOURS);
        }
    }

    private String generateFilePath() {
        DateTimeFormatter formatter;
        switch (rotationUnit) {
            case HOURS:
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH");
                break;
            case DAYS:
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                break;
            case MINUTES:
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm");
                break;
            default:
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH");
        }
        return basePath + "_" + currentRotationTime.format(formatter) + ".log";
    }

    private void checkRotation() {
        LocalDateTime now = LocalDateTime.now();
        if (currentRotationTime.truncatedTo(rotationUnit).isBefore(now.truncatedTo(rotationUnit))) {
            closeCurrentWriter();
            currentRotationTime = getCurrentRotationTime();
            currentFilePath = generateFilePath();
        }
    }

    private void ensureWriter() {
        if (currentWriter == null) {
            try {
                currentWriter = new PrintWriter(new FileWriter(currentFilePath, true));
            } catch (IOException e) {
                System.err.println("Ошибка создания файла: " + e.getMessage());
            }
        }
    }

    private void closeCurrentWriter() {
        if (currentWriter != null) {
            currentWriter.close();
            currentWriter = null;
        }
    }

    @Override
    public void handle(String message) {
        checkRotation();
        ensureWriter();
        if (currentWriter != null) {
            currentWriter.println(message);
            currentWriter.flush();
        }
    }
}