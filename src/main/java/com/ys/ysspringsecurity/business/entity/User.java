package com.ys.ysspringsecurity.business.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author ys
 * @date 2020/4/13 14:09
 */
@Entity
@Table(name = "user")
@Data
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "role")
  private String role;
}

