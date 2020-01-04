package com.github.rxyor.carp.auth.security.config;

import java.io.IOException;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/1/4 周六 21:52:00
 * @since 1.0.0
 */
@Configuration
@RefreshScope
@EnableConfigurationProperties({CarpAuthResourceProperties.class})
public class CarpAuthResourceAutoConfig {

    @Bean
    @Primary
    public RemoteTokenServices carpRemoteTokenServices(ResourceServerProperties properties,
        RestTemplate restTemplate) {
        RemoteTokenServices services = new RemoteTokenServices();
        services.setClientId(properties.getClientId());
        services.setClientSecret(properties.getClientSecret());
        services.setRestTemplate(restTemplate);
        services.setCheckTokenEndpointUrl(properties.getTokenInfoUri());
        return services;
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
}
