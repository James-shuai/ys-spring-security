package com.ys.ysspringsecurity.model;

import lombok.Data;

/**
 * @author ys
 * @date 2020/4/13 14:30
 */
@Data
public class LoginUser {

  private String username;
  private String password;
  private Integer rememberMe;

}
