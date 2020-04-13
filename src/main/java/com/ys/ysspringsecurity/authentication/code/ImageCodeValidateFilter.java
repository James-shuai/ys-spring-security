package com.ys.ysspringsecurity.authentication.code;

import com.alibaba.fastjson.JSONObject;
import com.ys.ysspringsecurity.authentication.CustomAuthenticationFailureHandler;
import com.ys.ysspringsecurity.business.controller.CustomLoginController;
import com.ys.ysspringsecurity.config.porperites.SecurityPorperites;
import com.ys.ysspringsecurity.utils.GetRequestJsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OncePerRequestFilter:所有请求之前被调用一次
 * @author ys
 * @date 2020/4/10 14:06
 */
@Component("imageCodeValidateFilter")
public class ImageCodeValidateFilter extends OncePerRequestFilter {

  @Autowired
  private SecurityPorperites securityPorperites;
  @Autowired
  private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
    //如果是POST的登录请求 则先校验验证码
    if (securityPorperites.getAuthentication().getLoginProcessingUrl().equals(httpServletRequest.getRequestURI())&&"POST".equalsIgnoreCase(httpServletRequest.getMethod())){
      try {
        //校验验证码
        validate(httpServletRequest);
      } catch (AuthenticationException e) {
        //交给失败处理器处理异常
        customAuthenticationFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
        return;
      }
    }

    //放行请求
    filterChain.doFilter(httpServletRequest,httpServletResponse);

  }

  private void validate(HttpServletRequest httpServletRequest) {
    //获取session里面的验证码
    String sessionImageCode = (String)httpServletRequest.getSession().getAttribute(CustomLoginController.SESSION_KEY);
    //获取用户输入的验证码
    String inputCode="";
    try {
      inputCode = GetRequestJsonUtils.getRequestJsonObject(httpServletRequest).get("code").toString();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (StringUtils.isBlank(inputCode)){
      throw new ValiDateCodeException("验证码不能为空");
    }
    if (!inputCode.equalsIgnoreCase(sessionImageCode)){
      throw new ValiDateCodeException("验证码不正确");
    }
  }
}
