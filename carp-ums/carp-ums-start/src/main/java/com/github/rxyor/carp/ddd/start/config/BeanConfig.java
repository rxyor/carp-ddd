package com.github.rxyor.carp.ddd.start.config;

import com.github.rxyor.common.support.spring.context.SpringBeanHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/28 周六 11:31:00
 * @since 1.0.0
 */
@Configuration
public class BeanConfig {

    @Bean
    @Primary
    public SpringBeanHolder springBeanHolder(){
        return new SpringBeanHolder();
    }
}
