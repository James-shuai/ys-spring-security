package com.ys.ysspringsecurity.business.entity;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author ys
 * @date 2020/4/16 14:20
 */
@Entity
@Table(name = "sys_user", schema = "ceshi", catalog = "")
public class SysUser implements UserDetails {
  private long id;
  private String username;
  private String password;
  private boolean isAccountNonExpired=true;
  private boolean isAccountNonLocked=true;
  private boolean isCredentialsNonExpired=true;
  private boolean isEnabled=true;
  private String nickName;
  private String mobile;
  private String email;
  private Date createDate;
  private Date updateDate;

  private List<String> auth;

  private Collection<? extends GrantedAuthority> authorities;
  /**
   * 拥有角色集合
   */

  private List<SysRole> roleList;

  private List<Long> roleIds;

  @Transient
  public List<Long> getRoleIds() {
    if(CollectionUtils.isNotEmpty(roleList)) {
      for(SysRole role : roleList) {
        roleIds.add(role.getId());
      }
    }
    return roleIds;
  }
  @Transient
  private Map<String,Object> permissions;



  @Id
  @Column(name = "id", nullable = false)
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Basic
  @Column(name = "username", nullable = false, length = 50)
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Transient
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
    this.authorities = authorities;
  }

  @Basic
  @Column(name = "password", nullable = false, length = 64)
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Basic
  @Column(name = "is_account_non_expired", nullable = true)
  public boolean isAccountNonExpired() {
    return isAccountNonExpired;
  }

  public void setAccountNonExpired(boolean accountNonExpired) {
    isAccountNonExpired = accountNonExpired;
  }

  @Basic
  @Column(name = "is_account_non_locked", nullable = true)
  public boolean isAccountNonLocked() {
    return isAccountNonLocked;
  }

  public void setAccountNonLocked(boolean accountNonLocked) {
    isAccountNonLocked = accountNonLocked;
  }

  @Basic
  @Column(name = "is_credentials_non_expired", nullable = true)
  public boolean isCredentialsNonExpired() {
    return isCredentialsNonExpired;
  }

  public void setCredentialsNonExpired(boolean credentialsNonExpired) {
    isCredentialsNonExpired = credentialsNonExpired;
  }

  @Basic
  @Column(name = "is_enabled", nullable = true)
  public boolean isEnabled() {
    return isEnabled;
  }

  public void setEnabled(boolean enabled) {
    isEnabled = enabled;
  }

  @Basic
  @Column(name = "nick_name", nullable = true, length = 64)
  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  @Basic
  @Column(name = "mobile", nullable = true, length = 20)
  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  @Basic
  @Column(name = "email", nullable = true, length = 50)
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Basic
  @Column(name = "create_date", nullable = false)
  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  @Basic
  @Column(name = "update_date", nullable = false)
  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }
  @Transient
  public List<SysRole> getRoleList() {
    return roleList;
  }

  public void setRoleList(List<SysRole> roleList) {
    this.roleList = roleList;
  }

  public void setRoleIds(List<Long> roleIds) {
    this.roleIds = roleIds;
  }
  @Transient
  public Map<String,Object> getPermissions() {
    return permissions;
  }

  public void setPermissions(Map<String,Object> permissions) {
    this.permissions = permissions;
  }
  @Transient
  public List<String> getAuth() {
    return auth;
  }

  public void setAuth(List<String> auth) {
    this.auth = auth;
  }
}
