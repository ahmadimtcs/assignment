package com.mahesh.weather.exceptionhandler;

public class BadDataException extends RuntimeException {

  private String message;

  public BadDataException(String message) {
    super(message);
    this.message = message;
  }

}
