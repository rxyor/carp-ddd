package com.github.rxyor.carp.ums.insfrastructure.feign.user;

import com.github.rxyor.carp.ums.api.dto.ums.UserRetDTO;
import com.github.rxyor.carp.query.service.user.UserQryService;
import com.github.rxyor.common.core.model.R;
import io.swagger.annotations.Api;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@Api(tags = "feign-user")
@AllArgsConstructor
@RestController
@RequestMapping("/feign/user")
public class UserFeign {

    private final UserQryService userQryService;

    /**
     * find by username
     *
     * @param username
     * @return
     */
    @GetMapping("/get/username")
    public R<UserRetDTO> find(
        @NotBlank(message = "用户名不能为空")
        @RequestParam("username") String username) {
        return R.success(userQryService.find(username));
    }
}
