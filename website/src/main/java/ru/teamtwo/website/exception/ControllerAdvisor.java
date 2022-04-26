package ru.teamtwo.website.exception;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<ErrorMessage> itemDoesntExistExceptionHandler (
            CartItemNotFoundException e) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                e.getMessage(),
                "Item does not exist");

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
