package com.ys.ysspringsecurity.config.porperites;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ys
 * @date 2020/4/10 10:07
 */
@Component
@ConfigurationProperties(prefix = "ys.security")
public class SecurityPorperites {

  private AuthenticationPorperties authentication;

  public AuthenticationPorperties getAuthentication() {
    return authentication;
  }

  public void setAuthentication(AuthenticationPorperties authentication) {
    this.authentication = authentication;
  }
}
