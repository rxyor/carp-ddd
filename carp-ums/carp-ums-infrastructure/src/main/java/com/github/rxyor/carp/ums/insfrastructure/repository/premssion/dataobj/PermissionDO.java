package com.github.rxyor.carp.ums.insfrastructure.repository.premssion.dataobj;

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
 * 权限表
 */
@Data
@Table(name = "ums_permission")
@Entity
public class PermissionDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Long id;

    /**
     * 权限编码
     */
    @Column(name = "permission_code", nullable = false)
    private String permissionCode = "";

    /**
     * 权限名称
     */
    @Column(name = "permission_name", nullable = false)
    private String permissionName = "";

    /**
     * 权限描述
     */
    @Column(name = "remark", insertable = false)
    private String remark = "";

    /**
     * 禁用标识, 0:启用, 1:禁用
     */
    @Column(name = "disable", nullable = false)
    private Integer disable = 0;

    /**
     * 创建时间
     */
    @Column(name = "create_time", insertable = false)
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time", insertable = false)
    private Date updateTime;

}