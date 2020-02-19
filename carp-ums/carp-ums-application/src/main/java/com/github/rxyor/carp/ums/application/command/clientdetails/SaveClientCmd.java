package com.github.rxyor.carp.ums.application.command.clientdetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/27 周五 23:11:00
 * @since 1.0.0
 */
@Data
public class SaveClientCmd {

    /**
     * 客户端ID(Http Basic 用户名)
     */
    @NotBlank(message = "客户端id不能为空")
    private String clientId;

    /**
     * 客户端密码(Http Basic 密码)
     */
    @NotBlank(message = "客户端密码不能为空")
    private String clientSecret;

    /**
     * (Http Basic)认证方式(可多选),[password:密码,authorization_code:授权码,refresh_token:刷新令牌,implicit:隐式,client_credentials:客户端证书]
     */
    @NotBlank(message = "认证方式不能为空")
    private String authorizedGrantTypes;

    /**
     * 可访问范围
     */
    @NotBlank(message = "访问范围不能为空")
    private String scope;

    /**
     * 权限
     */
    @NotBlank(message = "权限不能为空")
    private String authorities;

    /**
     * 资源ID
     */
    @NotBlank(message = "资源ID不能为空")
    private String resourceIds;

    /**
     * 令牌时效(秒)
     */
    @NotNull(message = "令牌时效不能为空")
    private Integer accessTokenValidity;

    /**
     * 刷新令牌时效(秒)
     */
    @NotNull(message = "刷新令牌时效不能为空")
    private Integer refreshTokenValidity;

    /**
     * 授权码认证方式(单选)[true,false,read,write]
     */
    @NotBlank(message = "授权码认证方式不能为空")
    private String autoapprove;

    /**
     * 授权码模式跳转URL
     */
    private String webServerRedirectUri;

    /**
     * 附加信息
     */
    private String additionalInformation;
}
