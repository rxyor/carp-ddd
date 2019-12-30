package com.github.rxyor.carp.api.feign.user.factory;

import com.github.rxyor.carp.api.feign.user.UserFeignService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

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
    public UserFeignService create(Throwable throwable) {
        return null;
    }
}
