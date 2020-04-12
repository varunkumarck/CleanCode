package com.webtwinz.learning.marksheet.exception;

//Improvement: Identify all the possible exceptions and wrap with Application specific exception
public class InvalidMarkException extends RuntimeException {

  public InvalidMarkException(Exception ex) {
    super(ex.getMessage(), ex);
  }
}
