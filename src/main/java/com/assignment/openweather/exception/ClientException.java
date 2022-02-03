package com.assignment.openweather.exception;

public class ClientException extends RuntimeException {

  private String message;
  private Integer statusCode;

  public ClientException(String message, Integer statusCode) {
    super();
    this.message = message;
    this.statusCode = statusCode;
  }

  public ClientException() {
    super();
  }

  public ClientException(String message) {
    super(message);
  }

  public ClientException(String message, Throwable cause) {
    super(message, cause);
  }

  public ClientException(Throwable cause) {
    super(cause);
  }

  protected ClientException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
