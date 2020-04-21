package com.ys.ysspringsecurity.business.entity;

import org.apache.commons.collections.CollectionUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author ys
 * @date 2020/4/16 14:25
 */
@Entity
@Table(name = "sys_role", schema = "ceshi", catalog = "")
public class SysRole {
  private long id;
  private String name;
  private String remark;
  private Date createDate;
  private Date updateDate;

  /**
   * 存储当前角色的权限资源对象集合
   * 修改角色时用到
   */

  private List<SysPermission> perList;
  /**
   * 存储当前角色的权限资源ID集合
   * 修改角色时用到
   */

  private List<Long> perIds;

  @Transient
  public List<Long> getPerIds() {
    if(CollectionUtils.isNotEmpty(perList)) {
      for(SysPermission per : perList) {
        perIds.add(per.getId());
      }
    }
    return perIds;
  }
  @Transient
  public List<SysPermission> getPerList() {
    return perList;
  }

  public void setPerList(List<SysPermission> perList) {
    this.perList = perList;
  }

  public void setPerIds(List<Long> perIds) {
    this.perIds = perIds;
  }

  @Id
  @Column(name = "id", nullable = false)
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Basic
  @Column(name = "name", nullable = false, length = 64)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Basic
  @Column(name = "remark", nullable = true, length = 200)
  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SysRole sysRole = (SysRole) o;
    return id == sysRole.id &&
      Objects.equals(name, sysRole.name) &&
      Objects.equals(remark, sysRole.remark) &&
      Objects.equals(createDate, sysRole.createDate) &&
      Objects.equals(updateDate, sysRole.updateDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, remark, createDate, updateDate);
  }
}
