package com.github.rxyor.carp.auth.security.support.security.core;

import com.github.rxyor.carp.auth.security.config.CarpAuthClientProperties;
import com.github.rxyor.carp.auth.security.consts.SecurityConst.Prefix;
import com.github.rxyor.carp.ums.api.dto.ums.PermissionRetDTO;
import com.github.rxyor.carp.ums.api.dto.ums.RoleRetDTO;
import com.github.rxyor.carp.ums.api.dto.ums.UserRetDTO;
import com.github.rxyor.carp.ums.api.enums.common.DisableEnum;
import com.github.rxyor.carp.ums.api.feign.user.UserFeignService;
import com.github.rxyor.common.core.exception.BizException;
import com.github.rxyor.common.core.model.R;
import com.github.rxyor.common.support.util.CryptoUtil;
import com.github.rxyor.common.support.util.RedisKeyBuilder;
import com.github.rxyor.common.util.lang2.BeanUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 15:33:00
 * @since 1.0.0
 */
@AllArgsConstructor
public class CarpUserDetailsService implements UserDetailsService {

    private final CarpAuthClientProperties carpAuthClientProperties;

    private final UserFeignService userFeignService;
    private final RedissonClient redissonClient;

    @Override
    public Oauth2User loadUserByUsername(String username) throws UsernameNotFoundException {
        final String key = RedisKeyBuilder.append("CarpUserDetailsService::" + username);
        RBucket<Oauth2User> bucket = redissonClient.getBucket(key);
        if (!bucket.isExists()) {
            R<UserRetDTO> ret = userFeignService.get(username, CryptoUtil.sign(60L));
            if (!R.isRequestSuccessCanNotNullData(ret)) {
                throw new BizException("请求用户信息失败");
            }
            Oauth2User user = this.toCarpUser(ret.getData());
            if (user != null) {
                bucket.set(user);
                bucket.expire(carpAuthClientProperties.getAccessTokenValiditySeconds().longValue(), TimeUnit.SECONDS);
            }
            return user;
        }
        return bucket.get();
    }

    private Oauth2User toCarpUser(UserRetDTO user) {
        if (user == null) {
            return null;
        }
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.addAll(BeanUtil.copy(this.getRoles(user), SimpleGrantedAuthority.class));
        return Oauth2User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .enabled(DisableEnum.ENABLE.getCode().equals(user.getDisable()))
            .accountNonExpired(true)
            .accountNonLocked(true)
            .credentialsNonExpired(true)
            .authorities(authorities)
            .build();
    }

    /**
     *获取用户的角色和权限
     *添加角色，角色需要加"ROLE_"前缀，原因：
     * 见{@link  ExpressionUrlAuthorizationConfigurer}
     *
     * @author liuyang
     * @date 2019-04-07 Sun 17:55:03
     * @param user UserDTO
     * @return GrantedAuthority List
     */
    private List<GrantedAuthority> getRoles(UserRetDTO user) {
        Set<String> authoritySet = new HashSet<>();
        //添加角色
        List<RoleRetDTO> roles = Optional.ofNullable(user.getRoleList())
            .orElse(new ArrayList<>(0));

        for (RoleRetDTO role : roles) {
            if (role == null) {
                continue;
            }
            if (StringUtils.isNotBlank(role.getRoleCode())) {
                //添加角色
                authoritySet.add(Prefix.ROLE + role.getRoleCode());
            }
            //添加权限
            List<PermissionRetDTO> permissions = Optional.ofNullable(role.getPermissionList())
                .orElse(new ArrayList<>(0));
            permissions.forEach(permissionDTO -> Optional.ofNullable(permissionDTO)
                .map(PermissionRetDTO::getPermissionCode)
                .filter(StringUtils::isNotBlank)
                .ifPresent(authoritySet::add));
        }
        return AuthorityUtils.createAuthorityList(authoritySet.toArray(new String[0]));
    }
}
