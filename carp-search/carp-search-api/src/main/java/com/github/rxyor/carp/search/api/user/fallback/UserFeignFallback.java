package com.github.rxyor.carp.search.api.user.fallback;

import com.github.rxyor.carp.search.api.dto.ums.UserRetDTO;
import com.github.rxyor.carp.search.api.user.UserFeignService;
import com.github.rxyor.common.core.model.R;
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
@SuppressWarnings("all")
@Slf4j
@Component
public class UserFeignFallback implements UserFeignService {

    @Setter
    private Throwable cause;

    @Override
    public R<UserRetDTO> save(UserRetDTO userRetDTO, String sign) {
        log.error("feign user fail:", cause);
        return null;
    }
}
