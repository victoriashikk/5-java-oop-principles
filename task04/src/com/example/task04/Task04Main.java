package com.example.task04;

public class Task04Main {
    public static void main(String[] args) {
        // Создаем логгер
        Logger logger = Logger.getLogger("myApp");
        logger.setLevel(Logger.Level.INFO);

        logger.addHandler(new ConsoleHandler());

        logger.info("Приложение запущено");
        logger.warning("Предупреждение: %d файлов не найдено", 5);
        logger.error("Критическая ошибка");

        Logger dbLogger = Logger.getLogger("database");
        dbLogger.addHandler(new ConsoleHandler());
        dbLogger.info("Подключение к базе данных установлено");
    }
}
