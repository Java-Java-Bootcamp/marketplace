package ru.teamtwo.api.logging;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

@Slf4j
public class LoggingUtils {
    public static void logException(Logger logger, Exception exception){
        logger.error(exception.getMessage());
    }

    public static void logException(Logger logger, String message, Exception exception){
        logger.error(message + ": " + exception.getMessage());
    }
}
