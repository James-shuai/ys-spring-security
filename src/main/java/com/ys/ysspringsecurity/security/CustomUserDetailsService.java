package com.ys.ysspringsecurity.security;

import com.alibaba.fastjson.JSON;
import com.ys.ysspringsecurity.business.entity.SysPermission;
import com.ys.ysspringsecurity.business.entity.SysUser;
import com.ys.ysspringsecurity.business.repository.SysPermissionRepository;
import com.ys.ysspringsecurity.business.repository.UserRepository;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 查询数据库中用户信息
 * <p>
 * 使用springSecurity需要实现UserDetailsService接口供权限框架调用.
 * 该方法只需要实现一个方法就可以了，那就是根据用户名去获取用户，那就是上面repository定义的方法了，这里直接调用了。
 *
 * @author ys
 * @date 2020/4/10 10:33
 */
@Component("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

  public Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  public PasswordEncoder passwordEncoder;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private SysPermissionRepository sysPermissionRepository;
//  @Autowired
//  private PublicRepository publicRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    logger.info("请求认证的用户名：" + username);

    SysUser user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("该用户不存在！");
    }
    //查询用户权限
    List<SysPermission> sysPermissionList = sysPermissionRepository.findByUserId(user.getId());
    if (CollectionUtils.isEmpty(sysPermissionList)) {
      return user;
    }
//    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    List<String> grantedAuthorities = new ArrayList<>();
    for (SysPermission sp : sysPermissionList) {
      //权限标识
//      grantedAuthorities.add(new SimpleGrantedAuthority(sp.getCode()));
      grantedAuthorities.add(sp.getCode());
    }
//    user.setAuthorities(grantedAuthorities);
    user.setAuth(grantedAuthorities);
    //交给spring security 进行身份认证
    return user;
  }

  // 把json格式的字符串写到文件
  public boolean writeFile(String filePath, String sets) {
    FileWriter fw;
    try {
      fw = new FileWriter(filePath);
      PrintWriter out = new PrintWriter(fw);
      out.write(sets);
      out.println();
      fw.close();
      out.close();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }

  }

  /**
   * 获取所有菜单树
   *
   * @param menuList
   * @param parentID
   * @return
   */
  private List<SysPermission> getMenuTreeList(List<SysPermission> menuList,String parentID) {
    // 查找所有菜单
    List<SysPermission> childrenList = new ArrayList<>();
    menuList.stream()
      .filter(d -> String.valueOf(d.getParentId()).equals(parentID)).sorted(Comparator.comparing(SysPermission::getSort))
      .collect(Collectors.toList())
      .forEach(d -> {
        d.setChild(getMenuTreeList(menuList,String.valueOf(d.getId())));
        childrenList.add(d);
      });
    return childrenList;
  }


}
