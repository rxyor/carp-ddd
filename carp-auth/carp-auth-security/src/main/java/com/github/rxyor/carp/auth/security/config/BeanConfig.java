package com.github.rxyor.carp.auth.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rxyor.carp.auth.security.support.oauth2.provider.CarpAccessDeniedHandler;
import com.github.rxyor.carp.auth.security.support.oauth2.provider.CarpClientDetailsService;
import com.github.rxyor.carp.auth.security.support.oauth2.provider.CarpWebResponseExceptionTranslator;
import com.github.rxyor.carp.auth.security.support.security.core.CarpUserDetailsService;
import com.github.rxyor.carp.auth.security.support.security.web.ResourceAuthExceptionEntryPoint;
import com.github.rxyor.carp.ums.api.feign.user.UserFeignService;
import com.github.rxyor.common.support.util.RedisKeyBuilder;
import javax.sql.DataSource;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 17:43:00
 * @since 1.0.0
 */
@Configuration
public class BeanConfig {

    @Value("${spring.application.name}")
    private String appName;

    @Bean
    @Primary
    public CarpUserDetailsService carpUserDetailsService(
        UserFeignService userFeignService,
        CarpAuthClientProperties carpAuthClientProperties,
        RedissonClient redissonClient) {
        return new CarpUserDetailsService(carpAuthClientProperties, userFeignService, redissonClient);
    }

    @Bean
    @Primary
    public CarpClientDetailsService carpClientDetailsService(DataSource dataSource) {
        return new CarpClientDetailsService(dataSource);
    }

    @Bean
    @Primary
    public ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint(ObjectMapper objectMapper) {
        return new ResourceAuthExceptionEntryPoint(objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean
    public SwaggerConfig swaggerConfig() {
        return new SwaggerConfig();
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisKeyBuilder redisKeyBuilder() {
        return new RedisKeyBuilder(appName, "::");
    }

    @Bean
    public CarpWebResponseExceptionTranslator webResponseExceptionTranslator(){
        return new CarpWebResponseExceptionTranslator();
    }

    @Bean
    public CarpAccessDeniedHandler accessDeniedHandler(ObjectMapper objectMapper){
        return new CarpAccessDeniedHandler(objectMapper);
    }
}
