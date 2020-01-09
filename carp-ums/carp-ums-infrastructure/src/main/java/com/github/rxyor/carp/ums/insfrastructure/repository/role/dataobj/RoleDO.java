package com.github.rxyor.carp.ums.insfrastructure.repository.role.dataobj;

import com.github.rxyor.carp.ums.insfrastructure.repository.premssion.dataobj.PermissionDO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

/**
 * 角色表
 */
@Table(name = "ums_role")
@Entity
@Data
public class RoleDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Long id;

    /**
     * 角色编码
     */
    @Column(name = "role_code", nullable = false)
    private String roleCode;

    /**
     * 角色名称
     */
    @Column(name = "role_name", nullable = false)
    private String roleName;

    /**
     * 角色描述
     */
    @Column(name = "remark", nullable = false)
    private String remark;

    /**
     * 禁用标识, 0:启用, 1:禁用
     */
    @Column(name = "disable", nullable = false)
    private Integer disable = 0;

    /**
     * 创建时间
     */
    @Column(name = "create_time",insertable = false)
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time",insertable = false)
    private Date updateTime;

    /**
     * 权限列表
     */
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "ums_role_permission_link",
        joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", unique = true),
        inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id", unique = true))
    private List<PermissionDO> permissionList = new ArrayList<>(8);

}