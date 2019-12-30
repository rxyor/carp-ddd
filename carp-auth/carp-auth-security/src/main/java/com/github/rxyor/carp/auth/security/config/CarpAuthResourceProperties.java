package com.github.rxyor.carp.auth.security.config;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

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
@Configuration
@RefreshScope
@ConditionalOnExpression("!'{carp.security.oauth2.resource}'.isEmpty()")
@ConfigurationProperties(prefix = "carp.security.oauth2.resource")
public class CarpAuthResourceProperties {

    /**
     * 是否开启资源权限校验
     */
    private Boolean enable= true;

    /**
     * 放行终端配置，网关不校验此处的终端
     */
    private List<String> whitelabelClients = new ArrayList<>();

    /**
     * 放行url,放行的url不再被安全框架拦截
     */
    private List<String> whitelabelUrls = new ArrayList<>();

    /**
     * 不聚合swagger
     */
    private List<String> swaggerProviders = new ArrayList<>();

}
