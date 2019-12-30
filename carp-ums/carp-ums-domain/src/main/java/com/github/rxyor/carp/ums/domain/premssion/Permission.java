package com.github.rxyor.carp.ums.domain.premssion;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 权限表
 */
@Data
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

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