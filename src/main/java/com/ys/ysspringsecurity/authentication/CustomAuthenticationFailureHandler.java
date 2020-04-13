package com.ys.ysspringsecurity.authentication;

import com.alibaba.fastjson.JSON;
import com.ys.ysspringsecurity.config.porperites.LoginResponseType;
import com.ys.ysspringsecurity.config.porperites.SecurityPorperites;
import com.ys.ysspringsecurity.utils.YsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器
 *
 * @author ys
 * @date 2020/4/10 11:17
 */
@Component("customAuthenticationFailureHandler")
//public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  @Autowired
  SecurityPorperites securityProperties;


  @Override
  public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
    if (LoginResponseType.JSON.equals(securityProperties.getAuthentication().getLoginResponseType())) {
      //1、认证失败后响应json字符串
      YsResult result = YsResult.build(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
      httpServletResponse.setContentType("application/json;charset=UTF-8");
      httpServletResponse.getOutputStream().write(result.toJsonString().getBytes("UTF-8"));
    } else {
      //默认失败地址
      super.setDefaultFailureUrl(securityProperties.getAuthentication().getLoginPage()+"?error");
      super.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
    }

  }
}
