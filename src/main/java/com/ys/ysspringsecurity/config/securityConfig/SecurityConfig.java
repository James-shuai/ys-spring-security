package com.ys.ysspringsecurity.config.securityConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ys.ysspringsecurity.authentication.code.ImageCodeValidateFilter;
import com.ys.ysspringsecurity.config.porperites.SecurityPorperites;
import com.ys.ysspringsecurity.security.CustomAuthenticationFilter;
import com.ys.ysspringsecurity.utils.YsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * spring security 配置类
 *
 * @author ys
 * @date 2020/4/9 11:07
 */
@Configuration
@EnableWebSecurity //开启security 过滤链 底层filter实现
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  //注入配置文件信息
  @Autowired
  private SecurityPorperites securityPorperites;
  //自定义认证信息
  @Autowired
  UserDetailsService customUserDetailsService;
  //自定义认证成功处理器
  @Autowired
  AuthenticationSuccessHandler customAuthenticationSuccessHandler;
  //自定义认证失败处理器
  @Autowired
  AuthenticationFailureHandler customAuthenticationFailureHandler;
  //自定义校验验证码处理
  @Autowired
  ImageCodeValidateFilter imageCodeValidateFilter;


  @Bean
  public PasswordEncoder PasswordEncoder() {
    //明文+随机盐值 加密存储
    return new BCryptPasswordEncoder();
  }

  /**
   * 认证管理器
   * 1、认证信息
   *
   * @param auth
   * @throws Exception
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customUserDetailsService);
  }

  /**
   * 资源权限配置
   * 1、被拦截的资源
   *
   * @param http
   * @throws Exception
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
//    http.httpBasic() //采用httpBasic认证方式
    http.addFilterBefore(imageCodeValidateFilter, UsernamePasswordAuthenticationFilter.class) //先校验验证码再校验用户名和密码
      .formLogin() //采用表单认证方式
      .loginPage(securityPorperites.getAuthentication().getLoginPage()) //自定义登录页面
      .and()
      .authorizeRequests() //认证请求
      .antMatchers(
        securityPorperites.getAuthentication().getLoginPage(),
        securityPorperites.getAuthentication().getLoginProcessingUrl(),"/index.html",
        "/api/code/image").permitAll() //放行所有请求 不需要认证
      .anyRequest().authenticated() //所有访问该应用的http请求都需要通过身份认证才可以访问
      .and()
      .csrf().disable()
    ;
    http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
  }



  /**
   * 一般针对静态资源放行
   *
   * @param web
   * @throws Exception
   */
  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers(securityPorperites.getAuthentication().getStaticPaths());
  }



  @Bean
  CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
    CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
    filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);//自定义认证成功处理器
    filter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);//自定义认证失败处理器
    filter.setFilterProcessesUrl(securityPorperites.getAuthentication().getLoginProcessingUrl());//登录表单请求路径，默认/login
    //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
    filter.setAuthenticationManager(authenticationManagerBean());
    return filter;
  }

}
