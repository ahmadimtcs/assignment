package com.mahesh.weather.exceptionhandler;

public class BadDataException extends RuntimeException {

  String message;

  public BadDataException(String message) {
    super(message);
    this.message = message;
  }

}
