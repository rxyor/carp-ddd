package com.github.rxyor.carp.auth.start.endpoint;

import com.github.rxyor.common.core.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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
@Api(tags = "token操作")
@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
public class CarpTokenPoint {

    private final TokenStore tokenStore;

    @ApiOperation(value = "移除当前登录用户Token", httpMethod = "POST")
    @PostMapping("/token/remove")
    public R<Boolean> logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        if (StringUtils.isBlank(authHeader)) {
            return R.<Boolean>fail().msg("请求无法获取token,删除token失败");
        }
        String tokenValue = authHeader.replace("Bearer", "").trim();
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
        if (accessToken == null || StringUtils.isBlank(accessToken.getValue())) {
            return R.<Boolean>fail().msg("找不到对应token, 删除token失败");
        }
        tokenStore.removeAccessToken(accessToken);
        return R.success(Boolean.TRUE);
    }

}
