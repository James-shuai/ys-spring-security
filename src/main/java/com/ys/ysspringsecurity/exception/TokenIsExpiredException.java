package com.ys.ysspringsecurity.exception;

/**
 *
 * @author ys
 * @date 2020/4/21 11:44
 */
public class TokenIsExpiredException extends Exception {

  public TokenIsExpiredException() {
  }

  public TokenIsExpiredException(String message) {
    super(message);
  }

  public TokenIsExpiredException(String message, Throwable cause) {
    super(message, cause);
  }

  public TokenIsExpiredException(Throwable cause) {
    super(cause);
  }

  public TokenIsExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
