package com.github.rxyor.carp.auth.security.support.oauth2.common;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/1/4 周六 14:59:00
 * @since 1.0.0
 */
public class OAuth2AuthenticationSerializer implements ObjectDeserializer {

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        if (type == OAuth2Authentication.class) {
            try {
                JSONObject jsonObject = parser.parseObject();
                OAuth2Request request = parseOAuth2Request(jsonObject);
                UsernamePasswordAuthenticationToken authentication = jsonObject
                    .getObject("userAuthentication", UsernamePasswordAuthenticationToken.class);
                return (T) new OAuth2Authentication(request, authentication);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }

    private OAuth2Request parseOAuth2Request(JSONObject jsonObject) {
        JSONObject json = jsonObject.getObject("oAuth2Request", JSONObject.class);
        Map<String, String> requestParameters = json.getObject("requestParameters", Map.class);
        String clientId = json.getString("clientId");
        String grantType = json.getString("grantType");
        String redirectUri = json.getString("redirectUri");
        Boolean approved = json.getBoolean("approved");
        Set<String> responseTypes = json
            .getObject("responseTypes", new TypeReference<HashSet<String>>() {
            });
        Set<String> scope = json.getObject("scope", new TypeReference<HashSet<String>>() {
        });
        Set<String> authorities = json.getObject("authorities", new TypeReference<HashSet<String>>() {
        });
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>(0);
        if(authorities!=null&&!authorities.isEmpty()){
            authorities.forEach(s -> grantedAuthorities.add(new SimpleGrantedAuthority(s)));
        }
        Set<String> resourceIds = json
            .getObject("resourceIds", new TypeReference<HashSet<String>>() {
            });
        Map<String, Serializable> extensions = json
            .getObject("extensions", new TypeReference<HashMap<String, Serializable>>() {
            });

        OAuth2Request request = new OAuth2Request(requestParameters, clientId,
            grantedAuthorities , approved, scope, resourceIds, redirectUri, responseTypes, extensions);
        TokenRequest tokenRequest = new TokenRequest(requestParameters, clientId, scope, grantType);
        request.refresh(tokenRequest);
        return request;
    }


    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
