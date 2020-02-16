package com.github.rxyor.carp.auth.start.endpoint;

import com.github.rxyor.common.core.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019-04-23 Tue 15:57:00
 * @since 1.0.0
 */
@Slf4j
@Api(tags = "Oauth2 Token")
@RestController
@RequestMapping("/oauth2")
public class CarpTokenEndpoint {

    @Resource(name = "redisTokenStore")
    private TokenStore tokenStore;

    @Autowired
    private TokenEndpoint tokenEndpoint;

    @Autowired
    private CheckTokenEndpoint checkTokenEndpoint;

    @ApiOperation(value = "登出接口", httpMethod = "POST")
    @PostMapping("/token/remove")
    public R<Boolean> logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        if (StringUtils.isBlank(authHeader)) {
            return R.<Boolean>fail().msg("退出登录失败");
        }
        String token = authHeader.replace("Bearer", "").trim();
        try {
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
            if (accessToken == null || StringUtils.isBlank(accessToken.getValue())) {
                return R.<Boolean>fail().msg("退出登录失败");
            }
            tokenStore.removeAccessToken(accessToken);
        } catch (Throwable e) {
            log.error("Token[{}]退出登录失败, 错误:", token, e);
            return R.<Boolean>fail().msg("退出登录失败");
        }
        return R.success(Boolean.TRUE);
    }

    @ApiOperation(value = "登录接口", httpMethod = "GET")
    @RequestMapping(value = "/token/access", method = RequestMethod.GET)
    public R<OAuth2AccessToken> getAccessToken(Principal principal, @RequestParam
        Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        ResponseEntity<OAuth2AccessToken> ret = tokenEndpoint.getAccessToken(principal, parameters);
        return R.success(ret.getBody());
    }

    @ApiOperation(value = "登录接口", httpMethod = "POST")
    @RequestMapping(value = "/token/access", method = RequestMethod.POST)
    public R<OAuth2AccessToken> postAccessToken(Principal principal, @RequestParam
        Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        ResponseEntity<OAuth2AccessToken> ret = tokenEndpoint.postAccessToken(principal, parameters);
        return R.success(ret.getBody());
    }

    @RequestMapping(value = "/token/detail", method = RequestMethod.GET)
    public R<OAuth2AccessToken> detail(String accessToken) {
        return R.success(tokenStore.readAccessToken(accessToken));
    }

    @RequestMapping(value = "/token/refresh/get", method = RequestMethod.GET)
    public R<OAuth2RefreshToken> getRefreshToken(String refreshToken) {
        return R.success(tokenStore.readRefreshToken(refreshToken));
    }

    @RequestMapping(value = "/token/check")
    public R<Map<String, ?>> checkToken(String token) {
        return R.success(checkTokenEndpoint.checkToken(token));
    }
}
