package com.github.rxyor.carp.auth.security.support.token.store.redis.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.TypeUtils;
import com.google.common.base.Preconditions;
import java.nio.charset.Charset;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
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
public class FastjsonRedisTokenStoreSerializationStrategy implements RedisTokenStoreSerializationStrategy {

    private static ParserConfig config = new ParserConfig();

    static {
        init();
    }

    protected static void init() {
        //自定义oauth2序列化：DefaultOAuth2RefreshToken 没有setValue方法，会导致JSON序列化为null
        config.setAutoTypeSupport(true);
        config.putDeserializer(DefaultOAuth2RefreshToken.class, new DefaultOauth2RefreshTokenSerializer());
        config.putDeserializer(OAuth2Authentication.class, new OAuth2AuthenticationSerializer());
        config.addAccept("org.springframework.security.oauth2.provider.");
        config.addAccept("org.springframework.security.oauth2.provider.client");

        TypeUtils.addMapping("org.springframework.security.oauth2.provider.OAuth2Authentication",
            OAuth2Authentication.class);
        TypeUtils.addMapping("org.springframework.security.oauth2.provider.client.BaseClientDetails",
            BaseClientDetails.class);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        Preconditions.checkArgument(clazz != null,
            "clazz can't be null");
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        try {
            return JSON.parseObject(new String(bytes, IOUtils.UTF8), clazz, config);
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
            return JSON.toJSONBytes(object, SerializerFeature.WriteClassName,
                SerializerFeature.DisableCircularReferenceDetect);
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
