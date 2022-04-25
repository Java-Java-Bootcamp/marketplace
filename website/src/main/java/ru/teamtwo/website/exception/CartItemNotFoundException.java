package ru.teamtwo.website.exception;

public class CartItemNotFoundException extends RuntimeException {

    public CartItemNotFoundException(String msg) {
        super(msg);
    }
}
