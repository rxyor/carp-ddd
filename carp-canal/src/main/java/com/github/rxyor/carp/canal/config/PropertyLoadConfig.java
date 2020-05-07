package com.github.rxyor.carp.canal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {
    "classpath:config/properties/rocketmq.properties"
})
public class PropertyLoadConfig {

}
