package com.github.rxyor.carp.query.dto.clientdetails;

import java.util.Date;
import lombok.Data;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/19 周三 22:27:00
 * @since 1.0.0
 */
@Data
public class ClientDTO {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 客户端ID(Http Basic 用户名)
     */
    private String clientId;

    /**
     * 客户端密码(Http Basic 密码)
     */
    private String clientSecret;

    /**
     * (Http Basic)认证方式(可多选),[password:密码,authorization_code:授权码,refresh_token:刷新令牌,implicit:隐式,client_credentials:客户端证书]
     */
    private String authorizedGrantTypes;

    /**
     * 可访问范围
     */
    private String scope;

    /**
     * 权限
     */
    private String authorities;

    /**
     * 资源ID
     */
    private String resourceIds;

    /**
     * 令牌时效(秒)
     */
    private Integer accessTokenValidity;

    /**
     * 刷新令牌时效(秒)
     */
    private Integer refreshTokenValidity;

    /**
     * 授权码认证方式(单选)[true,false,read,write]
     */
    private String autoapprove;

    /**
     * 授权码模式跳转URL
     */
    private String webServerRedirectUri;

    /**
     * 附加信息
     */
    private String additionalInformation;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
