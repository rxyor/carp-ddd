package com.github.rxyor.carp.auth.security.support.oauth2.provider;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/1/3 周五 09:43:00
 * @since 1.0.0
 */
public class CarpRedisTokenStore implements TokenStore {

    private static final String ACCESS = "access:";
    private static final String AUTH_TO_ACCESS = "auth_to_access:";
    private static final String AUTH = "auth:";
    private static final String REFRESH_AUTH = "refresh_auth:";
    private static final String ACCESS_TO_REFRESH = "access_to_refresh:";
    private static final String REFRESH = "refresh:";
    private static final String REFRESH_TO_ACCESS = "refresh_to_access:";
    private static final String CLIENT_ID_TO_ACCESS = "client_id_to_access:";
    private static final String UNAME_TO_ACCESS = "uname_to_access:";

    private final RedissonClient redissonClient;

    @Getter
    @Setter
    private String prefix = "";

    @Getter
    @Setter
    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();


    public CarpRedisTokenStore(RedissonClient redissonClient) {
        Preconditions.checkArgument(redissonClient != null,
            "redissonClient can't be null");
        this.redissonClient = redissonClient;
    }


    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        RBucket<OAuth2Authentication> bucket = redissonClient.getBucket(wrapKey(AUTH+token));
        return bucket.get();
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        String accessKey = wrapKey(ACCESS + token.getValue());
        String authKey = wrapKey(AUTH + token.getValue());
        String authToAccessKey = wrapKey(AUTH_TO_ACCESS + authenticationKeyGenerator.extractKey(authentication));
        String approvalKey = wrapKey(UNAME_TO_ACCESS + getApprovalKey(authentication));
        String clientId = wrapKey(CLIENT_ID_TO_ACCESS + authentication.getOAuth2Request().getClientId());

        RBucket<OAuth2AccessToken> oAuth2AccessTokenRBucket = redissonClient.getBucket(accessKey);
        oAuth2AccessTokenRBucket.set(token);
        RBucket<OAuth2Authentication> oAuth2AuthenticationRBucket = redissonClient.getBucket(authKey);
        oAuth2AuthenticationRBucket.set(authentication);
        RBucket<OAuth2AccessToken> authToAccessKeyRBucket = redissonClient.getBucket(authToAccessKey);
        authToAccessKeyRBucket.set(token);
        if (!authentication.isClientOnly()) {
            RSet<OAuth2AccessToken> rSet = redissonClient.getSet(approvalKey);
            rSet.add(token);
        }
        RSet<OAuth2AccessToken> rSet = redissonClient.getSet(clientId);
        rSet.add(token);
        if (token.getExpiration() != null) {
            int seconds = token.getExpiresIn();
            redissonClient.getBucket(accessKey).expire(seconds, TimeUnit.SECONDS);
            redissonClient.getBucket(authKey).expire(seconds, TimeUnit.SECONDS);
            redissonClient.getBucket(authToAccessKey).expire(seconds, TimeUnit.SECONDS);
            redissonClient.getBucket(clientId).expire(seconds, TimeUnit.SECONDS);
            redissonClient.getBucket(approvalKey).expire(seconds, TimeUnit.SECONDS);
        }

        OAuth2RefreshToken refreshToken = token.getRefreshToken();
        if (refreshToken != null && refreshToken.getValue() != null) {
            String refreshToAccessKey = wrapKey(REFRESH_TO_ACCESS + token.getRefreshToken().getValue());
            String accessToRefreshKey = wrapKey(ACCESS_TO_REFRESH + token.getValue());
            RBucket<String> refreshToAccessRBucket = redissonClient.getBucket(refreshToAccessKey);
            refreshToAccessRBucket.set(token.getValue());
            RBucket<String> accessToRefreshRBucket = redissonClient.getBucket(accessToRefreshKey);
            accessToRefreshRBucket.set(token.getRefreshToken().getValue());

            if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
                ExpiringOAuth2RefreshToken expiringRefreshToken = (ExpiringOAuth2RefreshToken) refreshToken;
                Date expiration = expiringRefreshToken.getExpiration();
                if (expiration != null) {
                    int seconds = Long.valueOf((expiration.getTime() - System.currentTimeMillis()) / 1000L)
                        .intValue();
                    refreshToAccessRBucket.expire(seconds, TimeUnit.SECONDS);
                    accessToRefreshRBucket.expire(seconds, TimeUnit.SECONDS);
                }
            }
        }
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        String key = wrapKey(ACCESS + tokenValue);
        RBucket<DefaultOAuth2AccessToken> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        removeAccessToken(token.getValue());
    }

    public void removeAccessToken(String tokenValue) {
        String accessKey = wrapKey(ACCESS + tokenValue);
        String authKey = wrapKey(AUTH + tokenValue);
        String accessToRefreshKey = wrapKey(ACCESS_TO_REFRESH + tokenValue);

        RBucket<OAuth2AccessToken> oAuth2AccessTokenRBucket = redissonClient.getBucket(accessKey);
        OAuth2AccessToken accessToken = oAuth2AccessTokenRBucket.get();

        RBucket<OAuth2Authentication> oAuth2AuthenticationRBucket = redissonClient.getBucket(authKey);
        OAuth2Authentication authentication = oAuth2AuthenticationRBucket.get();

        oAuth2AccessTokenRBucket.delete();
        oAuth2AuthenticationRBucket.delete();
        redissonClient.getBucket(accessToRefreshKey).delete();
        if (authentication != null) {
            String key = authenticationKeyGenerator.extractKey(authentication);
            String authToAccessKey = wrapKey(AUTH_TO_ACCESS + key);
            String unameKey = wrapKey(UNAME_TO_ACCESS + getApprovalKey(authentication));
            String clientId = wrapKey(CLIENT_ID_TO_ACCESS + authentication.getOAuth2Request().getClientId());

            redissonClient.getBucket(authToAccessKey).delete();
            redissonClient.getBucket(authToAccessKey).delete();
            RSet<String> unameSet = redissonClient.getSet(unameKey);
            unameSet.remove(accessToken);
            RSet<String> clientIdSet = redissonClient.getSet(clientId);
            clientIdSet.remove(accessToken);
            redissonClient.getBucket(wrapKey(ACCESS + key)).delete();
        }
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        String refreshKey = wrapKey(REFRESH + refreshToken.getValue());
        String refreshAuthKey = wrapKey(REFRESH_AUTH + refreshToken.getValue());
        RBucket<OAuth2RefreshToken> refreshBucket = redissonClient.getBucket(refreshKey);
        refreshBucket.set(refreshToken);
        RBucket<OAuth2Authentication> refreshAuthBucket = redissonClient.getBucket(refreshAuthKey);
        refreshAuthBucket.set(authentication);
        if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
            ExpiringOAuth2RefreshToken expiringRefreshToken = (ExpiringOAuth2RefreshToken) refreshToken;
            Date expiration = expiringRefreshToken.getExpiration();
            if (expiration != null) {
                int seconds = Long.valueOf((expiration.getTime() - System.currentTimeMillis()) / 1000L)
                    .intValue();
                refreshBucket.expire(seconds, TimeUnit.SECONDS);
                refreshAuthBucket.expire(seconds, TimeUnit.SECONDS);
            }
        }
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        String key = wrapKey(REFRESH + tokenValue);
        RBucket<DefaultOAuth2RefreshToken> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return readAuthenticationForRefreshToken(token.getValue());
    }

    public OAuth2Authentication readAuthenticationForRefreshToken(String token) {
        String key = wrapKey(REFRESH_AUTH + token);
        RBucket<OAuth2Authentication> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        removeRefreshToken(token.getValue());
    }

    public void removeRefreshToken(String tokenValue) {
        String refreshKey = wrapKey(REFRESH + tokenValue);
        String refreshAuthKey = wrapKey(REFRESH_AUTH + tokenValue);
        String refresh2AccessKey = wrapKey(REFRESH_TO_ACCESS + tokenValue);
        String access2RefreshKey = wrapKey(ACCESS_TO_REFRESH + tokenValue);
        redissonClient.getBucket(refreshKey).delete();
        redissonClient.getBucket(refreshAuthKey).delete();
        redissonClient.getBucket(refresh2AccessKey).delete();
        redissonClient.getBucket(access2RefreshKey).delete();
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        removeAccessTokenUsingRefreshToken(refreshToken.getValue());
    }

    private void removeAccessTokenUsingRefreshToken(String refreshToken) {
        String key = wrapKey(REFRESH_TO_ACCESS + refreshToken);

        RBucket<String> oAuth2AccessTokenRBucket = redissonClient.getBucket(key);
        String accessToken = oAuth2AccessTokenRBucket.get();
        oAuth2AccessTokenRBucket.delete();
        if (StringUtils.isNotBlank(accessToken)) {
            removeAccessToken(accessToken);
        }
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        String key = authenticationKeyGenerator.extractKey(authentication);
        String redisKey = wrapKey(AUTH_TO_ACCESS + key);
        RBucket<OAuth2AccessToken> bucket = redissonClient.getBucket(redisKey);
        OAuth2AccessToken accessToken = bucket.get();
        if (accessToken != null) {
            OAuth2Authentication storedAuthentication = readAuthentication(accessToken.getValue());
            if ((storedAuthentication == null || !key
                .equals(authenticationKeyGenerator.extractKey(storedAuthentication)))) {
                storeAccessToken(accessToken, authentication);
            }
        }
        return accessToken;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        String approvalKey = wrapKey(UNAME_TO_ACCESS + getApprovalKey(clientId, userName));

        RBucket<List<OAuth2AccessToken>> bucket = redissonClient.getBucket(approvalKey);
        List<OAuth2AccessToken> auth2AccessTokens = bucket.get();
        if (auth2AccessTokens == null || !auth2AccessTokens.isEmpty()) {
            return Collections.<OAuth2AccessToken>unmodifiableCollection(new ArrayList<>(0));
        }
        return Collections.<OAuth2AccessToken>unmodifiableCollection(auth2AccessTokens);
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        String key = wrapKey(CLIENT_ID_TO_ACCESS + clientId);

        RBucket<List<OAuth2AccessToken>> bucket = redissonClient.getBucket(key);
        List<OAuth2AccessToken> auth2AccessTokens = bucket.get();
        if (auth2AccessTokens == null || !auth2AccessTokens.isEmpty()) {
            return Collections.<OAuth2AccessToken>unmodifiableCollection(new ArrayList<>(0));
        }
        return Collections.<OAuth2AccessToken>unmodifiableCollection(auth2AccessTokens);
    }

    private String wrapKey(String key) {
        if (StringUtils.isNotBlank(prefix)) {
            return prefix + key;
        }
        return key;
    }

    private static String getApprovalKey(OAuth2Authentication authentication) {
        String userName = authentication.getUserAuthentication() == null ? ""
            : authentication.getUserAuthentication().getName();
        return getApprovalKey(authentication.getOAuth2Request().getClientId(), userName);
    }

    private static String getApprovalKey(String clientId, String userName) {
        return clientId + (userName == null ? "" : ":" + userName);
    }
}
