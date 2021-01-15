package com.github.rxyor.carp.search.infrastructure.feign;

import com.github.rxyor.carp.search.api.consts.CryptoConst.Crypt;
import com.github.rxyor.carp.search.api.dto.ums.UserRetDTO;
import com.github.rxyor.carp.search.application.cmd.user.UserCmd;
import com.github.rxyor.carp.search.application.service.UserCmdService;
import com.github.rxyor.common.core.model.R;
import com.github.rxyor.common.support.hibernate.validate.HibValidatorHelper;
import com.github.rxyor.common.util.lang2.BeanUtil;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 14:19:00
 * @since 1.0.0
 */
@Validated
@Api(tags = "FeignUser")
@AllArgsConstructor
@RestController
@RequestMapping("/feign/user")
public class SearchUserFeign {

    private final UserCmdService userCmdService;

    @PostMapping("/feign/search/user/save")
    public R<UserRetDTO> save(@RequestBody UserRetDTO userRetDTO,
        @RequestHeader(Crypt.SECURITY_KEY) String sign) {
        HibValidatorHelper.validate(userRetDTO);

        UserCmd userCmd = BeanUtil.copy(userRetDTO, UserCmd.class);
        userCmdService.saveUser(userCmd);
        return R.success(userRetDTO);
    }
}
