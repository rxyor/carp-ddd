package com.github.rxyor.carp.ddd.domain.user;

import com.github.rxyor.carp.ddd.domain.role.Role;
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
 * 用户表
 */
@Table(name = "user")
@Data
@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", insertable = false, nullable = false)
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "username", nullable = false)
    private String username;

    /**
     * 密码
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * 手机号
     */
    @Column(name = "phone", nullable = false)
    private String phone;

    /**
     * 昵称
     */
    @Column(name = "nickname", nullable = false)
    private String nickname;

    /**
     * 随机盐
     */
    @Column(name = "salt", nullable = false)
    private String salt = "";

    /**
     * 禁用标识[0:启用, 1:禁用]
     */
    @Column(name = "disable", nullable = false)
    private Integer disable = 0;

    /**
     * 锁定标识[0:未锁定, 1:锁定]
     */
    @Column(name = "locked", nullable = false)
    private Integer locked = 0;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

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

    /**
     * 角色列表
     */
    private List<Role> roleList = new ArrayList<>(8);

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", unique = true))
    public List<Role> getRoleList() {
        return roleList;
    }
}