package ru.teamtwo.api.exception;

public class UnableToAddItemException  extends RuntimeException {

    public UnableToAddItemException(String msg) {
        super(msg);
    }
}