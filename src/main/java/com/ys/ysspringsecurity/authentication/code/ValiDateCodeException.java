package com.ys.ysspringsecurity.authentication.code;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常处理类
 * @author ys
 * @date 2020/4/10 14:16
 */
public class ValiDateCodeException extends AuthenticationException {
  public ValiDateCodeException(String msg, Throwable t) {
    super(msg, t);
  }

  public ValiDateCodeException(String msg) {
    super(msg);
  }
}
