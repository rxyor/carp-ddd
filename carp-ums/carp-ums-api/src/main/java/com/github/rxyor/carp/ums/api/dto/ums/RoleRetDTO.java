package com.github.rxyor.carp.ums.api.dto.ums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 12:49:00
 * @since 1.0.0
 */
@Data
public class RoleRetDTO implements Serializable {

    private static final long serialVersionUID = 3339586648362111817L;

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
    private List<PermissionRetDTO> permissionList = new ArrayList<>(8);


}
