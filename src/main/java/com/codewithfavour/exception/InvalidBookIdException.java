package com.codewithfavour.exception;

public class InvalidBookIdException extends RuntimeException {
    public InvalidBookIdException(String message) {
        super(message);
    }
}
