package ru.teamtwo.backend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.teamtwo.backend.logging.LoggingUtils;

@Slf4j
@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity itemNotFoundException (
            ItemNotFoundException e) {
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(UnableToAddItemException.class)
    public ResponseEntity unableToAddItemException (
            UnableToAddItemException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity exception(Exception e) {
        LoggingUtils.logException(log, "Controller error", e);
        return ResponseEntity.badRequest().build();
    }
}
