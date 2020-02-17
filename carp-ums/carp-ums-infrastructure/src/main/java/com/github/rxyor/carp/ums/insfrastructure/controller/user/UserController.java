package com.github.rxyor.carp.ums.insfrastructure.controller.user;

import com.github.rxyor.carp.query.dto.UserDTO;
import com.github.rxyor.carp.query.qry.UserQry;
import com.github.rxyor.carp.query.service.user.UserQryService;
import com.github.rxyor.carp.ums.application.command.user.SaveUserCmd;
import com.github.rxyor.carp.ums.application.service.user.UserCmdService;
import com.github.rxyor.carp.ums.insfrastructure.controller.user.request.SaveUserReq;
import com.github.rxyor.carp.ums.insfrastructure.controller.user.request.SaveUserReqMapper;
import com.github.rxyor.common.core.model.R;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/27 周五 18:38:00
 * @since 1.0.0
 */
@Api(tags = "用户")
@AllArgsConstructor
@RestController
@RequestMapping("/ums/user")
public class UserController {

    private final UserCmdService userCmdService;
    private final UserQryService userQryService;

    @ApiOperation("保存用户")
    @PostMapping("/save")
    public R<String> save(@RequestBody SaveUserReq req) {
        Preconditions.checkNotNull(req, "请求参数不能为空");

        SaveUserCmd cmd = SaveUserReqMapper.INSTANCE.toSaveUserCmd(req);
        userCmdService.save(cmd);
        return R.success("ok");
    }

    @ApiOperation("分页查询")
    @PostMapping("/page")
    public R<Page<UserDTO>> page(@RequestBody UserQry req) {
        Preconditions.checkNotNull(req, "查询参数不能为空");

        Page<UserDTO> page = userQryService.page(req);
        return R.success(page);
    }
}
