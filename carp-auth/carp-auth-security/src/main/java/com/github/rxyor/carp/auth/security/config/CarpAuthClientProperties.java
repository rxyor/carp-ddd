package com.github.rxyor.carp.auth.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 16:28:00
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "carp.security.oauth2.client")
public class CarpAuthClientProperties {

    /**
     * 客户端id
     */
    private String clientId = "client";

    /**
     * 客户端加密方式
     */
    private String clientSecret = "{noop}secret";

    /**
     * 客户端授权方式
     */
    private String[] authorizedGrantTypes;

    /**
     * 客户端SCOPE
     */
    private String[] scope;

    /**
     * access token 生命周期
     */
    private Integer accessTokenValiditySeconds = 3600;

    /**
     * refresh token 生命周期
     */
    private Integer refreshTokenValiditySeconds = 30 * 86400;

    /**
     * 是否支持refresh token
     */
    private Boolean supportRefreshToken = true;


}
