package com.github.rxyor.carp.auth.security.support.oauth2.provider;

import com.github.rxyor.carp.auth.common.util.BuildSignatureUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        DefaultOAuth2AccessToken result = new DefaultOAuth2AccessToken(accessToken);
        String tokenId = result.getValue();
        String newTokenId = BuildSignatureUtil.createSignature(tokenId);
        result.setValue(newTokenId);

        OAuth2RefreshToken refreshToken = result.getRefreshToken();
        if(refreshToken!=null){
            String refreshTokenId = refreshToken.getValue();
            String newRefreshTokenId = refreshTokenId;
            try {
                if(StringUtils.isNotBlank(refreshTokenId)&&refreshTokenId.replaceAll("-","").trim().length()==32){
                    newRefreshTokenId= BuildSignatureUtil.createSignature(refreshTokenId);
                }
            } catch (Exception e) {
               log.warn("refreshTokenId[%s]无法转换为压缩refreshToken");
            }
            DefaultOAuth2RefreshToken newRefreshToken = new DefaultOAuth2RefreshToken(newRefreshTokenId);
            result.setRefreshToken(newRefreshToken);
        }
        return result;
    }
}
