package com.github.rxyor.carp.auth.security.config;

import com.github.rxyor.carp.auth.security.support.redis.FastjsonRedisTokenStoreSerializationStrategy;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/29 周日 23:12:00
 * @since 1.0.0
 */
@AllArgsConstructor
@Configuration
public class RedisStoreConfig {

    @Value("${spring.application.name:carp-auth}")
    private final String appName;

    private final RedisConnectionFactory redisConnectionFactory;

    @Bean
    @Primary
    public RedisTokenStore redisTokenStore() {
        RedisTokenStore store = new RedisTokenStore(redisConnectionFactory);
        store.setPrefix(appName + "::" + "auth:");
        store.setSerializationStrategy(new FastjsonRedisTokenStoreSerializationStrategy());
        return store;
    }

}
