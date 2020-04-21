package com.ys.ysspringsecurity.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 403 自定义返回消息
 * @author ys
 * @date 2020/4/14 12:04
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {


  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.getWriter().write(new ObjectMapper().writeValueAsString(new CustomResponse("请登录", null)));
  }

  static class CustomResponse {
    private String message;
    private Object data;

    CustomResponse(String message, Object data) {
      this.message = message;
      this.data = data;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    public Object getData() {
      return data;
    }

    public void setData(Object data) {
      this.data = data;
    }
  }


}
