package com.ys.ysspringsecurity.business.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author ys
 * @date 2020/4/10 13:50
 */
@RestController
@RequestMapping("/api")
public class CustomLoginController {

  private Logger logger = LoggerFactory.getLogger(CustomLoginController.class);

  public static final String SESSION_KEY="IMAGE_DODE";

  @Autowired
  private DefaultKaptcha defaultKaptcha;

  @RequestMapping("/code/image")
  public void imageCode(HttpServletRequest request, HttpServletResponse response){
    try {
      //获取验证码字符串
      String code = defaultKaptcha.createText();
      logger.info("生成的图形验证码为："+code);
      //得到的字符串放入session中
      request.getSession().setAttribute(SESSION_KEY,code);
      //获取验证码图片
      BufferedImage image = defaultKaptcha.createImage(code);
      ServletOutputStream outputStream = response.getOutputStream();
      ImageIO.write(image,"jpg",outputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
