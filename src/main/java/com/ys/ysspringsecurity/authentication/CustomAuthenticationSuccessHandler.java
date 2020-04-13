package com.ys.ysspringsecurity.authentication;

import com.alibaba.fastjson.JSON;
import com.ys.ysspringsecurity.config.porperites.AuthenticationPorperties;
import com.ys.ysspringsecurity.config.porperites.LoginResponseType;
import com.ys.ysspringsecurity.config.porperites.SecurityPorperites;
import com.ys.ysspringsecurity.utils.YsResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证成功处理器
 * 1、决定响应json还是跳转页面 或者是认证成功后的其他处理
 * @author ys
 * @date 2020/4/10 11:00
 */
@Component("customAuthenticationSuccessHandler")
//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  SecurityPorperites securityPorperites;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
    if (LoginResponseType.JSON.equals(securityPorperites.getAuthentication().getLoginResponseType())){
      //1、认证成功后响应json字符串
      YsResult result = YsResult.ok("认证成功");
      httpServletResponse.setContentType("application/json;charset=UTF-8");
      httpServletResponse.getOutputStream().write(result.toJsonString().getBytes("UTF-8"));
    }else {
      //重定向到上次请求的地址上，引发跳转到认证页面的地址
      logger.info("authentication:"+ JSON.toJSONString(authentication));
      super.onAuthenticationSuccess(httpServletRequest,httpServletResponse,authentication);
    }

  }
}
