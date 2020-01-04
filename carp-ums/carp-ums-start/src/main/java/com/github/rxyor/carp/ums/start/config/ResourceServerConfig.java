package com.github.rxyor.carp.ums.start.config;

import com.github.rxyor.carp.auth.security.support.oauth2.resource.AbstractResourceServerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/1/4 周六 23:41:00
 * @since 1.0.0
 */
@EnableResourceServer
@Configuration
public class ResourceServerConfig extends AbstractResourceServerConfig {

    @Override
    public void configureRegistry(ExpressionInterceptUrlRegistry registry) throws Exception {

    }
}
