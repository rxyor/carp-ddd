package com.github.rxyor.carp.ums.domain.role;

import com.github.rxyor.carp.ums.domain.premssion.Permission;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 * 角色表
 */
@Data
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private Long id;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String remark;

    /**
     * 禁用标识, 0:启用, 1:禁用
     */
    private Integer disable;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 权限列表
     */
    private List<Permission> permissionList = new ArrayList<>(8);


}