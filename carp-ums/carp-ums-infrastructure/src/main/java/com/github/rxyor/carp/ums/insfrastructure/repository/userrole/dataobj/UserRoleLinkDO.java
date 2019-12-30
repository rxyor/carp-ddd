package com.github.rxyor.carp.ums.insfrastructure.repository.userrole.dataobj;

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
 * 用户角色关系表
 */
@Table(name = "user_role_link")
@Data
@Entity
public class UserRoleLinkDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 角色id
     */
    @Column(name = "role_id", nullable = false)
    private Long roleId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;


}