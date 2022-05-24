package ru.teamtwo.api.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity itemDoesntExistExceptionHandler (
            ItemNotFoundException e) {
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(UnableToAddItemException.class)
    public ResponseEntity CantAddItemExceptionHandler (
            ItemNotFoundException e) {
        return ResponseEntity.badRequest().build();
    }
}
