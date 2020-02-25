package com.github.rxyor.carp.auth.security.support.token.store.redis.jackson;

import com.alibaba.fastjson.util.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.base.Preconditions;
import java.nio.charset.Charset;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;

/**
 *<p>
 * Fastjson序列化会导致异常
 *</p>
 *
 * @author liuyang
 * @date 2019/12/29 周日 23:18:00
 * @since 1.0.0
 */
public class JacksonRedisTokenStoreSerializationStrategy implements RedisTokenStoreSerializationStrategy {

    protected static ObjectMapper mapper = new ObjectMapper();

    {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(OAuth2Authentication.class,
            new OAuth2AuthenticationJackson2Deserializer(OAuth2Authentication.class));
        mapper.registerModule(module);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        Preconditions.checkArgument(clazz != null,
            "clazz can't be null");
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        try {
            return mapper.readValue(new String(bytes, IOUtils.UTF8), clazz);
        } catch (Exception ex) {
            throw new SerializationException("Could not serialize: " + ex.getMessage(), ex);
        }
    }

    @Override
    public String deserializeString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return new String(bytes, IOUtils.UTF8);
    }

    @Override
    public byte[] serialize(Object object) {
        if (object == null) {
            return new byte[0];
        }

        try {
            return mapper.writeValueAsBytes(object);
        } catch (Exception ex) {
            throw new SerializationException("Could not serialize: " + ex.getMessage(), ex);
        }
    }

    @Override
    public byte[] serialize(String data) {
        if (data == null || data.length() == 0) {
            return new byte[0];
        }

        return data.getBytes(Charset.forName("utf-8"));
    }
}
