package com.github.rxyor.carp.auth.security.util;

import com.github.rxyor.carp.auth.security.support.security.core.Oauth2User;
import java.util.Optional;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/16 周日 17:32:00
 * @since 1.0.0
 */
@UtilityClass
public class AuthHolder {

    public static Oauth2User user() {
        Object o = Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .map(Authentication::getPrincipal)
            .orElse(null);

        if (o != null && (o instanceof Oauth2User)) {
            return (Oauth2User) o;
        }
        return null;
    }

}
