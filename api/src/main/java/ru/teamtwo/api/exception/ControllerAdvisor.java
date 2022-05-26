package ru.teamtwo.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
            ItemNotFoundException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity exception(Exception e) {
        log.error("Controller error: ", e);
        return ResponseEntity.badRequest().build();
    }
}
