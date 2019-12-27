package com.github.rxyor.carp.ddd.domain.role;

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
 * 角色表
 */
@Table(name = "role")
@Entity
@Data
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", insertable = false, nullable = false)
    private Long id;

    /**
     * 角色编码
     */
    @Column(name = "role_code", nullable = false)
    private String roleCode = "";

    /**
     * 角色名称
     */
    @Column(name = "role_name", nullable = false)
    private String roleName = "";

    /**
     * 角色描述
     */
    @Column(name = "remark", nullable = false)
    private String remark = "";

    /**
     * 禁用标识, 0:启用, 1:禁用
     */
    @Column(name = "disable", nullable = false)
    private Integer disable = 0;

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;


}