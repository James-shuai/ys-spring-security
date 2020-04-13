package com.ys.ysspringsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("business/entity/**")
public class YsSpringSecurityApplication {

  public static void main(String[] args) {
    SpringApplication.run(YsSpringSecurityApplication.class, args);
  }

}
