package com.github.rxyor.carp.auth.security.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rxyor.carp.auth.security.support.oauth2.common.DefaultOauth2RefreshTokenSerializer;
import com.github.rxyor.carp.auth.security.support.oauth2.common.OAuth2AuthenticationSerializer;
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
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

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
@EnableConfigurationProperties({CarpAuthClientProperties.class, CarpAuthResourceProperties.class})
public class CarpAuthAuthorizeAutoConfig {


    protected static void init() {
        //自定义oauth2序列化：DefaultOAuth2RefreshToken 没有setValue方法，会导致JSON序列化为null
        ParserConfig global = ParserConfig.getGlobalInstance();
        global.setAutoTypeSupport(true);
        global.putDeserializer(DefaultOAuth2RefreshToken.class, new DefaultOauth2RefreshTokenSerializer());
        global.putDeserializer(OAuth2Authentication.class, new OAuth2AuthenticationSerializer());
        global.addAccept("org.springframework.security.oauth2.provider.");
        global.addAccept("org.springframework.security.oauth2.provider.client");

        TypeUtils.addMapping("org.springframework.security.oauth2.provider.OAuth2Authentication",
            OAuth2Authentication.class);
        TypeUtils.addMapping("org.springframework.security.oauth2.provider.client.BaseClientDetails",
            BaseClientDetails.class);
    }

    static {
        init();
    }

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
    public CarpClientDetailsService carpClientDetailsService(DataSource dataSource,
        RedissonClient redissonClient) {
        return new CarpClientDetailsService(dataSource, redissonClient);
    }

    @Bean
    @Primary
    public ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint(ObjectMapper objectMapper) {
        return new ResourceAuthExceptionEntryPoint(objectMapper);
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisKeyBuilder redisKeyBuilder() {
        return new RedisKeyBuilder(appName, "::");
    }

    @Bean
    public CarpWebResponseExceptionTranslator webResponseExceptionTranslator() {
        return new CarpWebResponseExceptionTranslator();
    }

    @Bean
    public CarpAccessDeniedHandler carpAccessDeniedHandler(ObjectMapper objectMapper) {
        return new CarpAccessDeniedHandler(objectMapper);
    }

}
