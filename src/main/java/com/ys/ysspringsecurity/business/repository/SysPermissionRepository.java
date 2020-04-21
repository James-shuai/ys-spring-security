package com.ys.ysspringsecurity.business.repository;

import com.ys.ysspringsecurity.business.entity.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * @author ys
 * @date 2020/4/13 14:05
 */
public interface SysPermissionRepository extends JpaRepository<SysPermission, Integer> {

  @Query(value = "select distinct sp.* from sys_user su\n" +
    "left join sys_user_role sur on su.id=sur.user_id\n" +
    "left join sys_role sr on sr.id=sur.role_id\n" +
    "left join sys_role_permission srp on srp.role_id=sr.id\n" +
    "left join sys_permission sp on sp.id=srp.permission_id\n" +
    "where su.id=?1",nativeQuery = true)
  List<SysPermission> findByUserId(long userid);

  @Query(value = "select distinct sp.* from sys_user su\n" +
    "left join sys_user_role sur on su.id=sur.user_id\n" +
    "left join sys_role sr on sr.id=sur.role_id\n" +
    "left join sys_role_permission srp on srp.role_id=sr.id\n" +
    "left join sys_permission sp on sp.id=srp.permission_id\n" +
    "where sp.type=1 and su.id=?1 and sp.parent_id<>-1",nativeQuery = true)
  List<SysPermission> findNavMenuByUserId(long userid);

  SysPermission findByParentId(long parentId);

//  @Query(value = "select distinct sp.name title,sp.icon,sp.path href,'_self' target,sp.parent_id,sp.id,sp.sort from sys_user su\n" +
//    "left join sys_user_role sur on su.id=sur.user_id\n" +
//    "left join sys_role sr on sr.id=sur.role_id\n" +
//    "left join sys_role_permission srp on srp.role_id=sr.id\n" +
//    "left join sys_permission sp on sp.id=srp.permission_id\n" +
//    "where sp.type=1 and su.id=?1 and sp.parent_id<>-1",nativeQuery = true)
//  List<Map<String,Object>> findNavMenuByUserId(long userid);
}
