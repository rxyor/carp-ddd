package com.github.rxyor.carp.api.feign.user.fallback;

import com.github.rxyor.carp.api.feign.user.UserFeignService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 12:44:00
 * @since 1.0.0
 */
@Slf4j
@Component
public class UserFeignFallback implements UserFeignService {

    @Setter
    private Throwable cause;

}
