package com.github.rxyor.carp.search.api.dto.ums;

import java.io.Serializable;
import java.util.Date;

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
public class PermissionRetDTO implements Serializable {

    private static final long serialVersionUID = -7350242108052033850L;

    /**
     * 权限id
     */
    private Long id;

    /**
     * 权限编码
     */
    private String permissionCode;

    /**
     * 权限名称
     */
    private String permissionName;

    /**
     * 权限描述
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
}
