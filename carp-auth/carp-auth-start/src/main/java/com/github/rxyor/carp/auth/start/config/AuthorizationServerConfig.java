package com.github.rxyor.carp.auth.start.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rxyor.carp.auth.security.config.CarpAuthClientProperties;
import com.github.rxyor.carp.auth.security.consts.SecurityConst.TokenAccess;
import com.github.rxyor.carp.auth.security.support.oauth2.provider.CarpAccessDeniedHandler;
import com.github.rxyor.carp.auth.security.support.oauth2.provider.CarpClientDetailsService;
import com.github.rxyor.carp.auth.security.support.oauth2.provider.CarpTokenEnhancer;
import com.github.rxyor.carp.auth.security.support.oauth2.provider.CarpWebResponseExceptionTranslator;
import com.github.rxyor.carp.auth.security.support.security.web.AuthorizeAuthExceptionEntryPoint;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/2/13 Wed 16:18:00
 * @since 1.0.0
 */
@RefreshScope
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private CarpAuthClientProperties carpAuthClientProperties;

    @Resource(name = "redisTokenStore")
    private TokenStore tokenStore;

    @Resource
    private DataSource dataSource;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private ObjectMapper objectMapper;

    /**
     *配置客户端Client 数据库存储，并配置查询语句
     *
     * @author liuyang
     * @date 2019-04-01 Mon 15:54:13
     * @return clientDetailsService
     */
    private CarpClientDetailsService clientDetailsService() {
        CarpClientDetailsService clientDetailsService = new CarpClientDetailsService(dataSource);
        return clientDetailsService;
    }

    /**
     *配置token有效时长以及token生成器
     *
     * @author liuyang
     * @date 2019-04-01 Mon 15:52:26
     * @return DefaultTokenServices
     */
    public AuthorizationServerTokenServices authorizationServerTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenEnhancer(new CarpTokenEnhancer());
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setAccessTokenValiditySeconds(carpAuthClientProperties.getAccessTokenValiditySeconds());
        tokenServices.setRefreshTokenValiditySeconds(carpAuthClientProperties.getRefreshTokenValiditySeconds());
        tokenServices.setSupportRefreshToken(carpAuthClientProperties.getSupportRefreshToken());
        return tokenServices;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
            .tokenKeyAccess(TokenAccess.PERMIT_ALL)
            .checkTokenAccess(TokenAccess.PERMIT_ALL)
            .allowFormAuthenticationForClients()
            .accessDeniedHandler(new CarpAccessDeniedHandler(objectMapper))
            .authenticationEntryPoint(new AuthorizeAuthExceptionEntryPoint(objectMapper))
            .addObjectPostProcessor(new ObjectPostProcessor<ExceptionHandlingConfigurer>() {
                @Override
                public <O extends ExceptionHandlingConfigurer> O postProcess(O object) {
                    object.accessDeniedHandler(new CarpAccessDeniedHandler(objectMapper));
                    return (O) object.authenticationEntryPoint(new AuthorizeAuthExceptionEntryPoint(objectMapper));
                }
            });
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
            .pathMapping("/oauth/token", "/oauth2/token/access")
            .pathMapping("/oauth/check_token", "/oauth2/token/check")
            .authenticationManager(authenticationManager)
            .tokenServices(authorizationServerTokenServices())
            .userDetailsService(userDetailsService)
            .tokenStore(tokenStore)
            .exceptionTranslator(new CarpWebResponseExceptionTranslator());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }

}
