package com.ys.ysspringsecurity.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ys.ysspringsecurity.business.entity.SysUser;
import com.ys.ysspringsecurity.model.LoginUser;
import com.ys.ysspringsecurity.utils.JwtTokenUtils;
import com.ys.ysspringsecurity.utils.RedisUtils;
import com.ys.ysspringsecurity.utils.YsResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证
 *
 * JWTAuthenticationFilter继承于UsernamePasswordAuthenticationFilter
 * 该拦截器用于获取用户登录的信息，只需创建一个token并调用authenticationManager.authenticate()让spring-security去进行验证就可以了，
 * 不用自己查数据库再对比密码了，这一步交给spring去操作。
 * 这个操作有点像是shiro的subject.login(new UsernamePasswordToken())，验证的事情交给框架。
 * @author ys
 * @date 2020/4/13 14:27
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


  private AuthenticationManager authenticationManager;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
    super.setFilterProcessesUrl("/auth/login");
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,HttpServletResponse response) throws AuthenticationException {
    LoginUser loginUser = null;
    try {
      loginUser = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword(), new ArrayList<>())
    );
  }

  // 成功验证后调用的方法
  // 如果验证成功，就生成token并返回
  @Override
  protected void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain chain,
                                          Authentication authResult) throws IOException, ServletException {

    // 查看源代码会发现调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
    // 所以就是JwtUser啦
    SysUser jwtUser = (SysUser) authResult.getPrincipal();
    String token = JwtTokenUtils.createToken(jwtUser, false);
    // 返回创建成功的token
    // 但是这里创建的token只是单纯的token
    // 按照jwt的规定，最后请求的格式应该是 `Bearer token`
    RedisUtils.redisUtils.set(token,jwtUser.getUsername(),30);
    Map<String,Object> map = new HashMap<>();
    map.put("token",token);
    jwtUser.setPassword("");
    map.put("user",jwtUser);
    YsResult result = YsResult.ok("认证成功",map);
    response.setContentType("application/json;charset=UTF-8");
    response.getOutputStream().write(result.toJsonString().getBytes("UTF-8"));
//    response.setHeader("token", JwtTokenUtils.TOKEN_PREFIX + token);
  }

  // 这是验证失败时候调用的方法
  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
    YsResult result = YsResult.build(HttpStatus.UNAUTHORIZED.value(), failed.getMessage());
    response.setContentType("application/json;charset=UTF-8");
    response.getOutputStream().write(result.toJsonString().getBytes("UTF-8"));
  }


}
