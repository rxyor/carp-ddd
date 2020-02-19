package com.github.rxyor.carp.ums.insfrastructure.repository;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * KeyValue配置表
 */
@Entity
@Data
@Table(name = "key_value_config")
public class KvConfigDO {

    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Long id;

    /**
     * 键
     */
    @Column(name = "key", nullable = false)
    private String key;

    /**
     * 值
     */
    @Column(name = "value", nullable = false)
    private String value;

    /**
     * 描述或备注
     */
    @Column(name = "desc", insertable = false)
    private String desc;

    /**
     * 描述或备注
     */
    @Column(name = "app_id", insertable = false)
    private String appId;

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