package ru.teamtwo.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorMessage> itemDoesntExistExceptionHandler (
            ItemNotFoundException e) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                e.getMessage(),
                "Unable to retrieve item");

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnableToAddItemException.class)
    public ResponseEntity<ErrorMessage> CantAddItemExceptionHandler (
            ItemNotFoundException e) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                e.getMessage(),
                "Unable to add item");

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
