package com.github.rxyor.carp.auth.security.support.token.store.redis.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.Type;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/1/3 周五 20:43:00
 * @since 1.0.0
 */
public class DefaultOauth2RefreshTokenSerializer implements ObjectDeserializer {

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        if (type == DefaultOAuth2RefreshToken.class) {
            JSONObject jsonObject = parser.parseObject();
            String tokenId = jsonObject.getString("value");
            DefaultOAuth2RefreshToken refreshToken = new DefaultOAuth2RefreshToken(tokenId);
            return (T) refreshToken;
        }
        return null;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
