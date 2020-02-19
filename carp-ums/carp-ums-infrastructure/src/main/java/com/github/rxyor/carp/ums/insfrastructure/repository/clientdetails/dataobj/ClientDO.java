package com.github.rxyor.carp.ums.insfrastructure.repository.clientdetails.dataobj;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * 客户端信息表
 */
@Entity
@Table(name = "oauth_client_details")
@Data
public class ClientDO {

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Long id;

    /**
     * 客户端ID(Http Basic 用户名)
     */
    @Column(name = "client_id", nullable = false)
    private String clientId;

    /**
     * 客户端密码(Http Basic 密码)
     */
    @Column(name = "client_secret")
    private String clientSecret;

    /**
     * (Http Basic)认证方式(可多选),[password:密码,authorization_code:授权码,refresh_token:刷新令牌,implicit:隐式,client_credentials:客户端证书
     ]
     */
    @Column(name = "authorized_grant_types")
    private String authorizedGrantTypes;

    /**
     * 可访问范围
     */
    @Column(name = "scope")
    private String scope;

    /**
     * 权限(client_credentials授权方式不会读取用户权限，会该权限）
     */
    @Column(name = "authorities")
    private String authorities;

    /**
     * 资源ID
     */
    @Column(name = "resource_ids")
    private String resourceIds;

    /**
     * 令牌时效(秒)
     */
    @Column(name = "access_token_validity")
    private Integer accessTokenValidity;

    /**
     * 刷新令牌时效(秒)
     */
    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValidity;

    /**
     * 授权码认证方式(单选)[true,false,read,write]
     */
    @Column(name = "autoapprove")
    private String autoapprove;

    /**
     * 授权码模式跳转URL
     */
    @Column(name = "web_server_redirect_uri")
    private String webServerRedirectUri;

    /**
     * 附加信息
     */
    @Column(name = "additional_information")
    private String additionalInformation;

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