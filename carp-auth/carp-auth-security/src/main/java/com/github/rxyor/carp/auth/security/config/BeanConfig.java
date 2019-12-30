package com.github.rxyor.carp.auth.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rxyor.carp.auth.security.support.oauth2.CarpClientDetailsService;
import com.github.rxyor.carp.auth.security.support.oauth2.CarpUserDetailsService;
import com.github.rxyor.carp.auth.security.support.oauth2.ResourceAuthExceptionEntryPoint;
import com.github.rxyor.carp.ums.api.feign.user.UserFeignService;
import javax.sql.DataSource;
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

    @Bean
    @Primary
    public CarpUserDetailsService carpUserDetailsService(
        UserFeignService userFeignService,
        CarpAuthClientProperties carpAuthClientProperties) {
        return new CarpUserDetailsService(carpAuthClientProperties, userFeignService);
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
    public SwaggerConfig swaggerConfig(){
        return new SwaggerConfig();
    }

}
