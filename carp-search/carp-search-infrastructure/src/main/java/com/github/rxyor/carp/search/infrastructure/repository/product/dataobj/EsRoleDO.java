package com.github.rxyor.carp.search.infrastructure.repository.product.dataobj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
public class EsRoleDO implements Serializable {

    private static final long serialVersionUID = 3339586648362111817L;

    /**
     * 角色id
     */
    @Id
    private Long id;

    /**
     * 角色编码
     */
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String roleCode;

    /**
     * 角色名称
     */
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String roleName;

    /**
     * 角色描述
     */
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String remark;

    /**
     * 禁用标识, 0:启用, 1:禁用
     */
    @Field(type = FieldType.Integer)
    private Integer disable;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Date)
    private Date createTime;

    /**
     * 更新时间
     */
    @Field(type = FieldType.Date)
    private Date updateTime;

    /**
     * 权限列表
     */
    @Field(type = FieldType.Object)
    private List<EsPermissionDO> permissionList = new ArrayList<>(8);


}
