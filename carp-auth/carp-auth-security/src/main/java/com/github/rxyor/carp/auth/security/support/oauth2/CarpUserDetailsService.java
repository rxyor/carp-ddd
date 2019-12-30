package com.github.rxyor.carp.auth.security.support.oauth2;

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
import com.github.rxyor.spring.boot.cacheablettl.CacheableTtl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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

    @CacheableTtl(cacheNames = "CarpUserDetailsService", ttl = 600)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        R<UserRetDTO> ret = userFeignService.get(username, CryptoUtil.sign(60L));
        if (!R.isRequestSuccessCanNotNullData(ret)) {
            throw new BizException("请求用户信息失败");
        }
        return this.toCarpUser(ret.getData());
    }

    private UserDetails toCarpUser(UserRetDTO user) {
        if (user == null) {
            return null;
        }
        List<GrantedAuthority> authorities = this.getRoles(user);
        return User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .disabled(DisableEnum.DISABLE.getCode().equals(user.getDisable()))
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .authorities(authorities)
            .build();
    }

    /**
     *获取用户的角色和权限
     *添加角色，角色需要加"ROLE_"前缀，原因：
     * 见{@link  ExpressionUrlAuthorizationConfigurer#hasRole(String)} )}
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
