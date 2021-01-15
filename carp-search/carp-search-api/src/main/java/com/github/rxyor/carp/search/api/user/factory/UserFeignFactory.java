package com.github.rxyor.carp.search.api.user.factory;

import com.github.rxyor.carp.search.api.user.UserFeignService;
import com.github.rxyor.carp.search.api.user.fallback.UserFeignFallback;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 12:38:00
 * @since 1.0.0
 */
@Component
public class UserFeignFactory implements FallbackFactory<UserFeignService> {

    @Override
    public UserFeignService create(Throwable cause) {
        UserFeignFallback fallback = new UserFeignFallback();
        fallback.setCause(cause);
        return fallback;
    }
}
