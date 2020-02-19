package com.github.rxyor.carp.ums.insfrastructure.repository.user.dataobj;

import com.github.rxyor.carp.ums.insfrastructure.repository.role.dataobj.RoleDO;
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
@Table(name = "ums_user")
@Data
@Entity
public class UserDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     * 邮箱
     */
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * 昵称
     */
    @Column(name = "nickname", nullable = false)
    private String nickname;

    /**
     * 头像
     */
    @Column(name = "avatar", nullable = false)
    private String avatar;

    /**
     * 随机盐
     */
    @Column(name = "salt", nullable = false)
    private String salt = "";

    /**
     * 禁用标识[0:启用, 1:禁用]
     */
    @Column(name = "disable")
    private Integer disable = 0;

    /**
     * 锁定标识[0:未锁定, 1:锁定]
     */
    @Column(name = "locked")
    private Integer locked = 0;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

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

    /**
     * 角色列表
     */
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "ums_user_role_link",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id", unique = true))
    private List<RoleDO> roleList = new ArrayList<>(8);
}