package com.assignment.exception;

public class CityDataException extends RuntimeException {
  private String message;

  public CityDataException(String s) {
    super(s);
    this.message = s;
  }
}
