package com.github.rxyor.carp.auth.start.endpoint;

import com.github.rxyor.carp.auth.common.model.LoginUser;
import com.github.rxyor.carp.auth.common.model.Options;
import com.github.rxyor.carp.auth.security.support.security.core.Oauth2User;
import com.github.rxyor.carp.auth.security.util.RedisKey;
import com.github.rxyor.common.core.model.R;
import com.github.rxyor.common.util.lang2.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.endpoint.CheckTokenEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
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
@Api(tags = "Oauth2")
@RestController
@RequestMapping("/oauth2")
public class CarpTokenEndpoint implements InitializingBean {

    @Resource
    private RedissonClient redissonClient;

    @Resource(name = "redisTokenStore")
    private TokenStore tokenStore;

    @Autowired
    private TokenEndpoint tokenEndpoint;

    @Autowired
    private CheckTokenEndpoint checkTokenEndpoint;

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

    @ApiOperation(value = "登出接口", httpMethod = "POST")
    @PostMapping("/token/remove")
    public R<Boolean> logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        if (StringUtils.isBlank(authHeader)) {
            return R.<Boolean>fail().msg("退出登录失败");
        }

        String token = authHeader.replace("Bearer", "").trim();
        try {
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
            if (accessToken != null) {
                //删除缓存的用户信息
                OAuth2Authentication authentication = tokenStore.readAuthentication(token);
                Object o = Optional.ofNullable(authentication.getPrincipal()).orElse(null);
                if (o != null && o instanceof Oauth2User) {
                    String cacheKey = RedisKey.userDetails(((Oauth2User) o).getUsername());
                    redissonClient.getBucket(cacheKey).deleteAsync();
                }

                tokenStore.removeAccessToken(accessToken);
            }
        } catch (Throwable e) {
            log.error("Token[{}]退出登录失败, 错误:", token, e);
            return R.<Boolean>fail().msg("退出登录失败");
        }
        return R.success(Boolean.TRUE);
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

    @ApiOperation(value = "登录用户信息", httpMethod = "GET")
    @GetMapping(value = "/user")
    public R<LoginUser> getLoginUser(@RequestHeader("Authorization") String bearHeader) {
        String token = getToken(bearHeader);
        if (StringUtils.isBlank(token)) {
            return R.fail();
        }

        OAuth2Authentication authentication = tokenStore.readAuthentication(token);
        if (authentication == null) {
            return R.fail();
        }

        Object o = authentication.getPrincipal();
        if (o == null || !(o instanceof Oauth2User)) {
            return R.fail();
        }

        Oauth2User user = (Oauth2User) o;

        List<String> resources = Optional.ofNullable(user.getAuthorities())
            .orElse(new ArrayList<>(0))
            .stream().map(GrantedAuthority::getAuthority)
            .distinct().collect(Collectors.toList());

        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(user.getUsername());
        loginUser.setPhone(user.getPhone());
        loginUser.setEmail(user.getEmail());
        loginUser.setNickname(user.getNickname());
        loginUser.setAvatar(user.getAvatar());
        loginUser.setResources(resources);
        loginUser.setRoleList(BeanUtil.copy(user.getRoleList(), Options.class));
        loginUser.setPermissionList(BeanUtil.copy(user.getPermissionList(), Options.class));

        return R.success(loginUser);
    }

    private String getToken(String bearToken) {
        final String prefix = "Bearer ";

        if (StringUtils.isBlank(bearToken)) {
            return null;
        }
        if (!bearToken.startsWith(prefix)) {
            return null;
        }
        String token = bearToken.replace(prefix, "");
        if (StringUtils.isNotBlank(token)) {
            return token;
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        tokenEndpoint.setAllowedRequestMethods(new HashSet<>(
            Arrays.asList(HttpMethod.POST, HttpMethod.GET)));
    }
}
