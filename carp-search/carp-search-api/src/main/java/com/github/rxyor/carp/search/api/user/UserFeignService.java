package com.github.rxyor.carp.search.api.user;

import com.github.rxyor.carp.search.api.consts.AppNameConst;
import com.github.rxyor.carp.search.api.consts.CryptoConst.Crypt;
import com.github.rxyor.carp.search.api.dto.ums.UserRetDTO;
import com.github.rxyor.common.core.model.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 12:32:00
 * @since 1.0.0
 */
@FeignClient(value = AppNameConst.SEARCH)
public interface UserFeignService {

    @PostMapping("/feign/search/user/save")
    R<UserRetDTO> save(@RequestBody UserRetDTO userRetDTO,
        @RequestHeader(Crypt.SECURITY_KEY) String sign);
}
