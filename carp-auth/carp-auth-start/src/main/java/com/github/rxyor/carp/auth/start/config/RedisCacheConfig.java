package com.github.rxyor.carp.auth.start.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.github.rxyor.spring.boot.cacheablettl.TtlRedisCacheManager;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisCacheConfig {

    @Value("${spring.application.name:carp-auth}")
    private String appName;

    @SuppressWarnings("unchecked")
    @Bean
    public RedisSerializer redisSerializer() {
        return new GenericFastJsonRedisSerializer();
    }

    @SuppressWarnings("unchecked")
    @Bean
    public RedisCacheConfiguration redisCacheDefaultConfig(RedisSerializer redisSerializer) {
        return RedisCacheConfiguration.defaultCacheConfig()
            .computePrefixWith((cacheName) -> appName + "::" + cacheName + "::")
            .serializeValuesWith(SerializationPair.fromSerializer(redisSerializer))
            // default cache ttl: 10min
            .entryTtl(Duration.ofMillis(10 * 60 * 1000));
    }

    @Bean
    Map<String, RedisCacheConfiguration> redisCacheConfigurationMap(RedisSerializer redisSerializer) {
        Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>(4);
        return configurationMap;
    }

    @Bean
    public TtlRedisCacheManager ttlRedisCacheManager(RedisConnectionFactory redisConnectionFactory,
        RedisCacheConfiguration redisCacheDefaultConfig,
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap) {
        RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        return new TtlRedisCacheManager(cacheWriter, redisCacheDefaultConfig, redisCacheConfigurationMap);
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisSerializer redisSerializer,
        RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate(redisConnectionFactory);
        template.setDefaultSerializer(redisSerializer);
        template.afterPropertiesSet();
        return template;
    }

}
