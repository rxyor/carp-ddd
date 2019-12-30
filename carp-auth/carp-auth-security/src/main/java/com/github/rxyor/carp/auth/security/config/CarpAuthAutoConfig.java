package com.github.rxyor.carp.auth.security.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 22:54:00
 * @since 1.0.0
 */
@RefreshScope
@Configuration
@EnableConfigurationProperties(
    value = {CarpAuthClientProperties.class, CarpAuthResourceProperties.class})
public class CarpAuthAutoConfig {

}
