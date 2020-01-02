package com.github.rxyor.carp.auth.security.support.oauth2.resource;

import com.github.rxyor.carp.auth.security.config.CarpAuthResourceProperties;
import com.github.rxyor.carp.auth.security.support.security.web.ResourceAuthExceptionEntryPoint;
import java.io.IOException;
import javax.annotation.Resource;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.client.DefaultResponseErrorHandler;
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
public abstract class AbstractResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {

    protected final static String DEFAULT_RESOURCE_ID = "oauth2-resource";

    @Resource
    protected ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint;

    @Resource
    protected UserDetailsService userDetailsService;

    @Resource
    protected RemoteTokenServices shieldRemoteTokenServices;

    @Resource
    protected AccessDeniedHandler shieldAccessDeniedHandler;

    @Resource
    protected CarpAuthResourceProperties carpAuthResourceProperties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //允许使用iframe 嵌套，避免swagger-ui 不被加载的问题
        http.csrf().disable();
        http.formLogin().disable();
        http.headers().frameOptions().disable();
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

    /**
     *负载均衡RestTemplate
     *
     * @author liuyang
     * @date 2019-03-31 Sun 14:16:24
     * @return RestTemplate
     */
    @Bean
    @LoadBalanced
    RestTemplate lbRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != HttpStatus.BAD_REQUEST.value()) {
                    super.handleError(response);
                }
            }
        });
        return restTemplate;
    }

    private void holdUserDetail(ResourceServerSecurityConfigurer resources) {
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        DefaultUserAuthenticationConverter userAuthenticationConverter = new DefaultUserAuthenticationConverter();
        userAuthenticationConverter.setUserDetailsService(userDetailsService);

        shieldRemoteTokenServices.setRestTemplate(lbRestTemplate());
        shieldRemoteTokenServices.setAccessTokenConverter(accessTokenConverter);
        resources.authenticationEntryPoint(resourceAuthExceptionEntryPoint);
        resources.accessDeniedHandler(shieldAccessDeniedHandler);
        resources.tokenServices(shieldRemoteTokenServices);
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
    public abstract void configureResourceServer(ResourceServerSecurityConfigurer resources);
}
