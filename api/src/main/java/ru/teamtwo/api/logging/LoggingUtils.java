package ru.teamtwo.api.logging;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

@Slf4j
public class LoggingUtils {
    public static void logException(Logger l, Exception e){
        l.error(e.getMessage());
    }

    public static void logException(Logger l, String s, Exception e){
        l.error(s + e.getMessage());
    }
}
