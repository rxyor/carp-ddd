package com.github.rxyor.carp.auth.security.config;

import com.github.rxyor.carp.auth.security.support.oauth2.provider.CarpRedisTokenStore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@Configuration
public class RedisStoreConfig {

    @Value("${spring.application.name:carp-auth}")
    private final String appName;

    private final RedisConnectionFactory redisConnectionFactory;

    public RedisStoreConfig(@Value("${spring.application.name:carp-auth}") String appName,
        RedisConnectionFactory redisConnectionFactory) {
        this.appName = appName;
        this.redisConnectionFactory = redisConnectionFactory;
    }

//    @Bean
    public RedisTokenStore redisTokenStore() {
        RedisTokenStore store = new RedisTokenStore(redisConnectionFactory);
        store.setPrefix(appName + "::" + "auth:");
        return store;
    }

    @Bean
    public CarpRedisTokenStore redisTokenStore(RedissonClient redissonClient) {
        CarpRedisTokenStore store = new CarpRedisTokenStore(redissonClient);
        store.setPrefix(appName + "::" + "auth:");
        return store;
    }

}
