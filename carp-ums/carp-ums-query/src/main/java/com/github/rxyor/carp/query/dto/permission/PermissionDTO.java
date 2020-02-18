package com.github.rxyor.carp.query.dto.permission;

import java.util.Date;
import lombok.Data;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/18 周二 14:39:00
 * @since 1.0.0
 */
@Data
public class PermissionDTO {

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
