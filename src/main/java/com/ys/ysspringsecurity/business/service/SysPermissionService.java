package com.ys.ysspringsecurity.business.service;

import com.ys.ysspringsecurity.business.repository.SysPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ys
 * @date 2020/4/16 13:53
 */
@Service
public class SysPermissionService {

  @Autowired
  private SysPermissionRepository sysPermissionRepository;



}
