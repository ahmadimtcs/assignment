package com.assignment.exception;

public class CityNotFoundException extends RuntimeException{

    private String message;
    private Throwable ex;

    public CityNotFoundException(String message, Throwable ex) {
        super(message, ex);
        this.message = message;
        this.ex = ex;
    }

    public CityNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
