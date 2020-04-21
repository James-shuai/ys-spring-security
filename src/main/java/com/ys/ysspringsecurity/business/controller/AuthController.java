package com.ys.ysspringsecurity.business.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ys.ysspringsecurity.business.entity.SysUser;
import com.ys.ysspringsecurity.business.repository.SysPermissionRepository;
import com.ys.ysspringsecurity.business.repository.UserRepository;
import com.ys.ysspringsecurity.utils.RedisUtils;
import com.ys.ysspringsecurity.utils.YsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * 注册的控制器
 * @author ys
 * @date 2020/4/13 14:44
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

  // 为了减少篇幅就不写service接口了
  @Autowired
  private UserRepository userRepository;
  @Autowired
  public PasswordEncoder passwordEncoder;
  @Autowired
  public RedisUtils redisUtils;

  @PostMapping("/register")
  public String registerUser(@RequestBody Map<String,String> registerUser){
    SysUser user = new SysUser();
    user.setUsername(registerUser.get("username"));
    // 记得注册的时候把密码加密一下
    user.setPassword(passwordEncoder.encode(registerUser.get("password")));
    user.setNickName("超级管理员");
    user.setMobile("13888888888");
    user.setEmail("13888888888@qq.com");
    user.setCreateDate(new Date());
    SysUser save = userRepository.save(user);
    return save.toString();
  }

  /**
   * 校验token合法性
   * @return
   */
  @PostMapping("/checkToken")
  public Object checkToken(@RequestBody String request){
    try {
      JSONObject jsonObject = JSONObject.parseObject(request);
      String token = jsonObject.get("token").toString();
      boolean b = redisUtils.hasKey(token);
      if (!b){
        return YsResult.build(403,"token失效，请重新登录");
      }
      return YsResult.ok();
    } catch (Exception e) {
      e.printStackTrace();
      return YsResult.build(403,e.getMessage());
    }
  }

}

