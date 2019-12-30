package com.github.rxyor.carp.ums.api.feign.user;

import com.github.rxyor.carp.ums.api.consts.AppNameConst;
import com.github.rxyor.carp.ums.api.consts.CryptoConst.Crypt;
import com.github.rxyor.carp.ums.api.dto.ums.UserRetDTO;
import com.github.rxyor.common.core.model.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 12:32:00
 * @since 1.0.0
 */
@FeignClient(value = AppNameConst.UMS)
public interface UserFeignService {

    @GetMapping("/feign/user/get/username")
    R<UserRetDTO> get(@RequestParam("username") String username,
        @RequestHeader(Crypt.SECURITY_KEY) String sign);
}
