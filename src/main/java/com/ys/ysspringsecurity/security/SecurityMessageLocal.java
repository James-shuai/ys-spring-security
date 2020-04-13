package com.ys.ysspringsecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 中文的认证提示信息
 * @author ys
 * @date 2020/4/13 12:58
 */
@Configuration
public class SecurityMessageLocal {

  @Bean // 加载中文的认证提示信息
  public ReloadableResourceBundleMessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    //.properties 不要加到后面
    messageSource.setBasename("classpath:org/springframework/security/messages_zh_CN");
    return messageSource;
  }

}
