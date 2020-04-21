package com.ys.ysspringsecurity.business.controller;

import com.ys.ysspringsecurity.business.entity.SysPermission;
import com.ys.ysspringsecurity.business.repository.SysPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ys
 * @date 2020/4/16 15:21
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

  @Autowired
  private SysPermissionRepository sysPermissionRepository;

  @PreAuthorize("hasAuthority('sys:index')")
  @RequestMapping("/navMenu")
  public Object navMenu(HttpServletRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      if (authentication instanceof AnonymousAuthenticationToken) {
        System.out.println("weinull");
      }
      if (authentication instanceof UsernamePasswordAuthenticationToken) {
        Object principal = authentication.getPrincipal();
        System.out.println(principal);
      }
    }
    String userid = request.getParameter("userId");
    List<SysPermission> byUserId = sysPermissionRepository.findNavMenuByUserId(Integer.parseInt(userid));
    List<SysPermission> menuTreeList = getMenuTreeList(byUserId, "0");
    //首页
    SysPermission home = sysPermissionRepository.findByParentId(-1);
    Map<String, Object> homeMap = new HashMap<>();
    homeMap.put("title", home.getTitle());
    homeMap.put("href", home.getHref());
    Map<String, Object> logoMap = new HashMap<>();
    logoMap.put("title", "LAYUI MINI");
    logoMap.put("image", "images/logo.png");
    logoMap.put("href", "11");
    Map<String, Object> getMap = new HashMap<>();
    getMap.put("homeInfo", homeMap);
    getMap.put("logoInfo", logoMap);
    getMap.put("menuInfo", menuTreeList);
    return getMap;
  }


  /**
   * 获取所有菜单树
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
