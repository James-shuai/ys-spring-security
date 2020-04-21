package com.ys.ysspringsecurity.security;

import com.alibaba.fastjson.JSON;
import com.ys.ysspringsecurity.business.entity.SysUser;
import com.ys.ysspringsecurity.business.repository.UserRepository;
import com.ys.ysspringsecurity.utils.JwtTokenUtils;
import com.ys.ysspringsecurity.utils.RedisUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 鉴权
 * <p>
 * 验证成功当然就是进行鉴权了，每一次需要权限的请求都需要检查该用户是否有该权限去操作该资源，当然这也是框架帮我们做的，那么我们需要做什么呢？
 * 很简单，只要告诉spring-security该用户是否已登录，是什么角色，拥有什么权限就可以了。
 * JWTAuthenticationFilter继承于BasicAuthenticationFilter，至于为什么要继承这个我也不太清楚了，
 * 这个我也是网上看到的其中一种实现，实在springSecurity苦手，
 * 不过我觉得不继承这个也没事呢（实现以下filter接口或者继承其他filter实现子类也可以吧）只要确保过滤器的顺序，JWTAuthorizationFilter在JWTAuthenticationFilter后面就没问题了。
 *
 * @author ys
 * @date 2020/4/13 14:33
 */

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

  @Autowired
  private UserRepository userRepository;
  public static JWTAuthorizationFilter jwtAuthorizationFilter ;
  @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
  public void init() {
    jwtAuthorizationFilter = this;
    jwtAuthorizationFilter.userRepository = this.userRepository;
    //初使化时将已静态化的accessTokenOcrRepository实例化
  }


  public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain chain) throws IOException, ServletException {

    String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
    // 如果请求头中没有Authorization信息则直接放行了
    if (tokenHeader == null || !RedisUtils.redisUtils.hasKey(tokenHeader)) {
      chain.doFilter(request, response);
      return;
    }
    // 如果请求头中有token，则进行解析，并且设置认证信息
    RedisUtils.redisUtils.expire(tokenHeader, 30);
    SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
    super.doFilterInternal(request, response, chain);
  }

  // 这里从token中获取用户信息并新建一个token
  private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
    try {
      String token = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
      Claims claim = JwtTokenUtils.getClaim(token);
      String user = claim.get("user").toString();

//      JSONObject.toJavaObject(user,SysUser.class)
      SysUser sysUser = JSON.parseObject(user, SysUser.class);
      List<String> auth = sysUser.getAuth();
      List<GrantedAuthority> authorities = new ArrayList<>();
      for (String t:auth) {
        authorities.add(new SimpleGrantedAuthority(t));
      }
      if (sysUser != null) {
        return new UsernamePasswordAuthenticationToken(sysUser, null,authorities );
      }
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }


}
