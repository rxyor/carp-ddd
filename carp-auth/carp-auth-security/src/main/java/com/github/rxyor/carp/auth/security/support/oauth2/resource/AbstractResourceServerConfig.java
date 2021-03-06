package com.github.rxyor.carp.auth.security.support.oauth2.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rxyor.carp.auth.security.config.CarpAuthResourceProperties;
import com.github.rxyor.carp.auth.security.support.oauth2.provider.CarpAccessDeniedHandler;
import com.github.rxyor.carp.auth.security.support.oauth2.provider.CarpRemoteTokenServices;
import com.github.rxyor.carp.auth.security.support.security.web.AuthorizeAuthExceptionEntryPoint;
import com.github.rxyor.carp.auth.security.support.security.web.ResourceAuthExceptionEntryPoint;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.client.RestTemplate;

/**
 *<p>
 *抽象资源服务配置
 *</p>
 *
 * @author liuyang
 * @date 2019/3/19 Tue 23:27:00
 * @since 1.0.0
 */
@SuppressWarnings("all")
public abstract class AbstractResourceServerConfig extends ResourceServerConfigurerAdapter {

    protected final static String DEFAULT_RESOURCE_ID = "oauth2-resource";

    @Resource
    protected ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint;

    @Resource(name = "carpUserDetailsService")
    protected UserDetailsService userDetailsService;

    @Resource(name = "carpRemoteTokenServices")
    protected CarpRemoteTokenServices remoteTokenServices;

    @Resource(name = "carpAccessDeniedHandler")
    protected AccessDeniedHandler accessDeniedHandler;

    @Resource(name = "lbRestTemplate")
    protected RestTemplate restTemplate;

    @Resource
    protected CarpAuthResourceProperties carpAuthResourceProperties;

    @Resource
    private ResourceServerProperties resourceServerProperties;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //允许使用iframe 嵌套，避免swagger-ui 不被加载的问题
        http.csrf().disable();
        http.httpBasic().disable();
        http.formLogin().disable();
        http.headers().frameOptions().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        http.exceptionHandling().authenticationEntryPoint(new AuthorizeAuthExceptionEntryPoint(objectMapper));
        http.exceptionHandling().accessDeniedHandler(new CarpAccessDeniedHandler(objectMapper));
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
            .authorizeRequests();
        //是否开启资源权限校验
        if (!carpAuthResourceProperties.getEnable()) {
            registry.anyRequest().permitAll();
            return;
        }
        if (carpAuthResourceProperties != null && carpAuthResourceProperties.getWhitelabelUrls().size() > 0) {
            //忽略白名单url
            carpAuthResourceProperties.getWhitelabelUrls().forEach(s -> registry.antMatchers(s).permitAll());
        }
        this.configureRegistry(registry);
        registry.anyRequest().authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(DEFAULT_RESOURCE_ID);
        this.holdUserDetail(resources);
        this.configureResourceServer(resources);
    }

    private void holdUserDetail(ResourceServerSecurityConfigurer resources) {
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        DefaultUserAuthenticationConverter userAuthenticationConverter = new DefaultUserAuthenticationConverter();
        userAuthenticationConverter.setUserDetailsService(userDetailsService);
        //将用户信息通过SecurityContext绑定到ThreadLocal上下文时，
        // 需要调用userAuthenticationConverter的userDetailsService获取用户信息
        accessTokenConverter.setUserTokenConverter(userAuthenticationConverter);

        remoteTokenServices.setRestTemplate(restTemplate);
        remoteTokenServices.setAccessTokenConverter(accessTokenConverter);
        resources.authenticationEntryPoint(resourceAuthExceptionEntryPoint);
        resources.accessDeniedHandler(accessDeniedHandler);
        resources.tokenServices(remoteTokenServices);
    }

    /**
     * 子类配置registry
     *
     * @param registry url注册器
     * @throws Exception Exception
     */
    public abstract void configureRegistry(
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) throws Exception;

    /**
     * 子类配置resources
     *
     * @param resources 资源服务
     */
    public void configureResourceServer(ResourceServerSecurityConfigurer resources) {
        if (resourceServerProperties != null) {
            if (StringUtils.isNotEmpty(resourceServerProperties.getResourceId())) {
                resources.resourceId(resourceServerProperties.getResourceId());
            }
        }
    }
}
