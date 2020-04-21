package com.ys.ysspringsecurity.business.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author ys
 * @date 2020/4/16 14:26
 */
@Entity
@Table(name = "sys_permission", schema = "ceshi", catalog = "")
public class SysPermission {
  private long id;
  private Long parentId;
  private String title;
  private String code;
  private String href;
  private int type;
  private int sort;
  private String icon;
  private String remark;
  private Date createDate;
  private Date updateDate;

  /**
   * 用于新增和修改页面上默认的根菜单名称
   */

  private String parentName = "根菜单";
  private String target = "_self";
  /**
   * 所有子权限对象集合
   * 左侧菜单渲染时要用
   */

  private List<SysPermission> child;

  /**
   * 所有子菜单url
   */

  private List<String> childrenUrl;
  @Transient
  public String getParentName() {
    return parentName;
  }

  public void setParentName(String parentName) {
    this.parentName = parentName;
  }
  @Transient
  public List<SysPermission> getChild() {
    return child;
  }

  public void setChild(List<SysPermission> child) {
    this.child = child;
  }
  @Transient
  public List<String> getChildrenUrl() {
    return childrenUrl;
  }

  public void setChildrenUrl(List<String> childrenUrl) {
    this.childrenUrl = childrenUrl;
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
  @Column(name = "parent_id", nullable = true)
  public Long getParentId() {
    return parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  @Basic
  @Column(name = "name", nullable = false, length = 64)
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Basic
  @Column(name = "code", nullable = true, length = 64)
  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Basic
  @Column(name = "path", nullable = true, length = 255)
  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  @Basic
  @Column(name = "type", nullable = false)
  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  @Basic
  @Column(name = "icon", nullable = true, length = 200)
  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
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
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  @Basic
  @Column(name = "update_date", nullable = false)
  @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }
  @Basic
  @Column(name = "sort", nullable = false)
  public int getSort() {
    return sort;
  }

  public void setSort(int sort) {
    this.sort = sort;
  }
  @Transient
  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SysPermission that = (SysPermission) o;
    return id == that.id &&
      type == that.type &&
      Objects.equals(parentId, that.parentId) &&
      Objects.equals(title, that.title) &&
      Objects.equals(code, that.code) &&
      Objects.equals(href, that.href) &&
      Objects.equals(icon, that.icon) &&
      Objects.equals(remark, that.remark) &&
      Objects.equals(createDate, that.createDate) &&
      Objects.equals(updateDate, that.updateDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, parentId, title, code, href, type, icon, remark, createDate, updateDate);
  }
}
