package com.ys.ysspringsecurity;

import com.ys.ysspringsecurity.business.entity.SysPermission;
import com.ys.ysspringsecurity.business.repository.SysPermissionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
class YsSpringSecurityApplicationTests {

  @Autowired
  SysPermissionRepository sysPermissionRepository;

  @Test
   void contextLoads() {
    List<SysPermission> byUserId = sysPermissionRepository.findByUserId(9);
    System.out.println(byUserId);
  }


}
