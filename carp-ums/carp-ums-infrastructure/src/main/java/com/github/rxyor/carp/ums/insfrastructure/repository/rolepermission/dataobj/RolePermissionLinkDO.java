package com.github.rxyor.carp.ums.insfrastructure.repository.rolepermission.dataobj;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * 角色权限关系表
 */
@Entity
@Table(name = "role_permission_link")
@Data
public class RolePermissionLinkDO implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 自增主键
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", insertable = false, nullable = false)
  private Long id;

  /**
   * 角色id
   */
  @Column(name = "role_id", nullable = false)
  private Integer roleId;

  /**
   * 权限id
   */
  @Column(name = "permission_id")
  private Integer permissionId;

  /**
   * 创建时间
   */
  @Column(name = "create_time")
  private Date createTime;

  
}