package com.github.rxyor.carp.auth.security.support.oauth2.provider;

import com.github.rxyor.carp.auth.common.util.BuildSignatureUtil;
import com.github.rxyor.carp.auth.security.support.security.core.Oauth2User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/1/2 周四 23:06:00
 * @since 1.0.0
 */
@Slf4j
public class CarpTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken ret = new DefaultOAuth2AccessToken(accessToken);
        String tokenId = ret.getValue();
        String newTokenId = BuildSignatureUtil.createSignature(tokenId);
        ret.setValue(newTokenId);

        OAuth2RefreshToken refreshToken = ret.getRefreshToken();
        if (refreshToken != null) {
            String refreshTokenId = refreshToken.getValue();
            String newRefreshTokenId = refreshTokenId;
            try {
                if (StringUtils.isNotBlank(refreshTokenId)
                    && refreshTokenId.replaceAll("-", "").trim().length() == 32) {
                    newRefreshTokenId = BuildSignatureUtil.createSignature(refreshTokenId);
                }
            } catch (Exception e) {
                log.warn("refreshTokenId[%s]无法转换为压缩refreshToken");
            }
            DefaultOAuth2RefreshToken newRefreshToken = new DefaultOAuth2RefreshToken(newRefreshTokenId);
            ret.setRefreshToken(newRefreshToken);
        }
        Map<String, Object> additionalInformation = getUserInfo(authentication);
        ret.setAdditionalInformation(additionalInformation);
        return ret;
    }

    private Map<String, Object> getUserInfo(OAuth2Authentication authentication) {
        Oauth2User user = (Oauth2User) authentication.getPrincipal();
        Map<String, Object> map = new HashMap<>(2);

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("username", user.getUsername());
        List<String> authorities = user.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .distinct().collect(Collectors.toList());
        userMap.put("authorities", authorities);
        userMap.put("enabled", user.isEnabled());

        map.put("user", userMap);

        return map;
    }
}
