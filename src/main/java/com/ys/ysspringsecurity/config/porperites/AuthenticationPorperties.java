package com.ys.ysspringsecurity.config.porperites;

import lombok.Data;

/**
 * @author ys
 * @date 2020/4/10 10:11
 */
@Data
public class AuthenticationPorperties {

  private String loginPage="/login.html";
  private String loginProcessingUrl="/login/form";
  private String usernameParameter="name";
  private String passwordParameter="pwd";
  private String[] staticPaths={"/dist/**", "/modules/**", "/plugins/**"};
  private LoginResponseType loginResponseType;

}
