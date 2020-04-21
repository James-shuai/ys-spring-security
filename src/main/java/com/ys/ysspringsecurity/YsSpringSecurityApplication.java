package com.ys.ysspringsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EntityScan("com.ys.ysspringsecurity.business.entity")
public class YsSpringSecurityApplication {

  public static void main(String[] args) {
    SpringApplication.run(YsSpringSecurityApplication.class, args);
  }
  /**
   * 解决跨域
   * @return
   */
  @Bean
  public WebMvcConfigurer webMvcConfigurer() {
    return new WebMvcConfigurerAdapter() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
      }
    };
  }
}
