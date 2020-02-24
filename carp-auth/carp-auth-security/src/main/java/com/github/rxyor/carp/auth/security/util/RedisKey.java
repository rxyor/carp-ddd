package com.github.rxyor.carp.auth.security.util;

import com.github.rxyor.common.support.util.RedisKeyBuilder;
import lombok.experimental.UtilityClass;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/24 周一 23:47:00
 * @since 1.0.0
 */
@UtilityClass
public class RedisKey {

    public static String userDetails(String username) {
        return RedisKeyBuilder.append("CarpUserDetailsService::" + username);
    }

}
