package com.github.rxyor.carp.auth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/1/3 周五 17:50:00
 * @since 1.0.0
 */

public class JsonTest extends JUnit5IT {

    @Test
    public void testJson() {
        ParserConfig config = ParserConfig.getGlobalInstance();
//        config.setAutoTypeSupport(true);
        String json = "{\"@type\":\"org.springframework.security.oauth2.common.DefaultOAuth2AccessToken\",\"additionalInformation\":{\"@type\":\"java.util.LinkedHashMap\"},\"expiration\":1578049911999,\"expired\":false,\"expiresIn\":5055,\"refreshToken\":{\"@type\":\"org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken\"},\"scope\":Set[\"server\"],\"tokenType\":\"bearer\",\"value\":\"zJ65Bnte0Mzgloz2\"}";
        Object o = JSON.parseObject(json, DefaultOAuth2AccessToken.class);
        System.out.println(o);
    }
}
