package com.github.rxyor.carp.search.infrastructure.repository.user.dataobj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 12:48:00
 * @since 1.0.0
 */
@Data
@Document(indexName = "ums_user", shards = 1, replicas = 0)
public class EsUserDO implements Serializable {

    private static final long serialVersionUID = 6737706257026877445L;

    /**
     * 自增主键
     */
    @Id
    private Long id;

    /**
     * 用户名
     */
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String username;

    /**
     * 密码
     */
    @Field(type = FieldType.Keyword)
    private String password;

    /**
     * 手机号
     */
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String phone;

    /**
     * 邮箱
     */
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String email;

    /**
     * 昵称
     */
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String nickname;

    /**
     * 头像
     */
    @Field(type = FieldType.Keyword)
    private String avatar;

    /**
     * 随机盐
     */
    @Field(type = FieldType.Keyword)
    private String salt;

    /**
     * 禁用标识[0:启用, 1:禁用]
     */
    @Field(type = FieldType.Integer)
    private Integer disable;

    /**
     * 锁定标识[0:未锁定, 1:锁定]
     */
    @Field(type = FieldType.Integer)
    private Integer locked;

    /**
     * 备注
     */
    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String remark;

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
     * 角色列表
     */
    @Field(type = FieldType.Object)
    private List<EsRoleDO> roleList = new ArrayList<>(8);
}
