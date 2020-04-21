package com.ys.ysspringsecurity.business.repository;

import com.ys.ysspringsecurity.business.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ys
 * @date 2020/4/13 14:05
 */
public interface UserRepository extends JpaRepository<SysUser, Integer> {
  SysUser findByUsername(String username);
}
