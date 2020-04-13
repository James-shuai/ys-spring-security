package com.ys.ysspringsecurity.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 查询数据库中用户信息
 * @author ys
 * @date 2020/4/10 10:33
 */
@Component()
public class CustomUserDetailsService implements UserDetailsService {

  public Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  public PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    logger.info("请求认证的用户名："+username);

    //1、通过用户名查询用户信息
    if (!"ys".equalsIgnoreCase(username)){
      throw new UsernameNotFoundException("用户名或密码错误");
    }
    //假设当前用户数据库存储密码为123456且加密
    String password=passwordEncoder.encode("123456");
    //2、查询用户权限

    //3、封装用户信息与权限信息
    User user = new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
    return user;
  }
}
