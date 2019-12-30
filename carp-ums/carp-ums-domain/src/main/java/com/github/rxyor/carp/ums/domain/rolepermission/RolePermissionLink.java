package com.github.rxyor.carp.ums.domain.rolepermission;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 角色权限关系表
 */
@Data
public class RolePermissionLink implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * 自增主键
   */
  private Integer id;

  /**
   * 角色id
   */
  private Integer roleId;

  /**
   * 权限id
   */
  private Integer permissionId;

  /**
   * 创建时间
   */
  private Date createTime;

  
}