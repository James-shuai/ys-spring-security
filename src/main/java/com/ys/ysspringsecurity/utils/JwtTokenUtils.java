package com.ys.ysspringsecurity.utils;

import com.alibaba.fastjson.JSON;
import com.ys.ysspringsecurity.business.entity.SysUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类
 * @author ys
 * @date 2020/4/13 14:03
 */
public class JwtTokenUtils {

  public static final String TOKEN_HEADER = "Authorization";
  public static final String TOKEN_PREFIX = "Bearer ";

  private static final String SECRET = "jwtsecretdemo";
  private static final String ISS = "echisan";

  // 过期时间是3600秒，既是1个小时
  private static final long EXPIRATION = 30L;

  // 选择了记住我之后的过期时间为7天
  private static final long EXPIRATION_REMEMBER = 604800L;

  // 创建token
  public static String createToken(SysUser sysUser, boolean isRememberMe) {
//    long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
    Map<String, Object> claims = new HashMap<>();
    claims.put("user", JSON.toJSONString(sysUser));
    return Jwts.builder()
      .signWith(SignatureAlgorithm.HS512, SECRET)
      .setIssuer(ISS)
      .setSubject(sysUser.getUsername())
      .setIssuedAt(new Date())
      .setClaims(claims)
      .compact();
  }

  // 从token中获取用户名
  public static String getUsername(String token){
    return getTokenBody(token).getSubject();
  }

  /**
   * 解析Claims
   *
   * @param token
   * @return
   */
  public static Claims getClaim(String token) {
    Claims claims = null;
    try {
      claims = Jwts.parser()
        .setSigningKey(SECRET)
        .parseClaimsJws(token)
        .getBody();
    } catch (Exception e) {
      e.printStackTrace();
      return claims;
    }
    return claims;
  }

  // 是否已过期
  public static boolean isExpiration(String token){
    return getTokenBody(token).getExpiration().before(new Date());
  }

  private static Claims getTokenBody(String token){
    return Jwts.parser()
      .setSigningKey(SECRET)
      .parseClaimsJws(token)
      .getBody();
  }


}
