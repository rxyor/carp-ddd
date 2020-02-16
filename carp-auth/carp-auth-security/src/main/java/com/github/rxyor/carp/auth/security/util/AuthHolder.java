package com.github.rxyor.carp.auth.security.util;

import com.github.rxyor.carp.auth.security.support.security.core.Oauth2User;
import lombok.experimental.UtilityClass;
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
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (o instanceof Oauth2User) {
            return (Oauth2User) o;
        }
        return null;
    }

}
