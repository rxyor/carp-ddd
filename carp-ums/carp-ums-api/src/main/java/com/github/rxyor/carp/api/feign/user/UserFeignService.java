package com.github.rxyor.carp.api.feign.user;

import com.github.rxyor.carp.api.consts.AppName;
import org.springframework.cloud.openfeign.FeignClient;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 12:32:00
 * @since 1.0.0
 */
@FeignClient(value = AppName.UMS)
public interface UserFeignService {

}
