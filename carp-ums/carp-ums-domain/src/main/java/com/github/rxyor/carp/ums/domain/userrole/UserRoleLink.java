package com.github.rxyor.carp.ums.domain.userrole;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户角色关系表
 */
@Data
public class UserRoleLink implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 创建时间
     */
    private Date createTime;


}