package com.github.rxyor.carp.auth.start.config;

import com.github.rxyor.carp.auth.security.config.CarpAuthClientProperties;
import com.github.rxyor.carp.auth.security.consts.SecurityConst.TokenAccess;
import com.github.rxyor.carp.auth.security.exhandler.CarpWebResponseExceptionTranslator;
import com.github.rxyor.carp.auth.security.support.oauth2.CarpClientDetailsService;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

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

    @Resource
    private RedisTokenStore redisTokenStore;

    @Resource
    private DataSource dataSource;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private UserDetailsService userDetailsService;

    /**
     *配置客户端Client 数据库存储，并配置查询语句
     *
     * @author liuyang
     * @date 2019-04-01 Mon 15:54:13
     * @return clientDetailsService
     */
    private CarpClientDetailsService clientDetailsService() {
        CarpClientDetailsService clientDetailsService = new CarpClientDetailsService(dataSource);
        clientDetailsService.setSelectClientDetailsSql(CarpClientDetailsService.DEFAULT_SELECT_STATEMENT);
        clientDetailsService.setFindClientDetailsSql(CarpClientDetailsService.DEFAULT_FIND_STATEMENT);
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
        tokenServices.setTokenStore(redisTokenStore);
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
            .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
            .pathMapping("/oauth/token","/oauth2/token/access")
            .authenticationManager(authenticationManager)
            .tokenServices(authorizationServerTokenServices())
            .userDetailsService(userDetailsService)
            .tokenStore(redisTokenStore)
            .exceptionTranslator(new CarpWebResponseExceptionTranslator());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }

}
