package com.github.rxyor.carp.ums.insfrastructure.controller.user;

import com.github.rxyor.carp.auth.security.support.security.core.Oauth2User;
import com.github.rxyor.carp.auth.security.util.AuthHolder;
import com.github.rxyor.carp.query.dto.user.UserDTO;
import com.github.rxyor.carp.query.qry.user.UserQry;
import com.github.rxyor.carp.query.service.user.UserQryService;
import com.github.rxyor.carp.ums.api.dto.ums.UserRetDTO;
import com.github.rxyor.carp.ums.api.enums.common.DisableEnum;
import com.github.rxyor.carp.ums.application.command.user.AllocRoleCmd;
import com.github.rxyor.carp.ums.application.command.user.DisableUserCmd;
import com.github.rxyor.carp.ums.application.command.user.SaveUserCmd;
import com.github.rxyor.carp.ums.application.command.user.UpdateUserCmd;
import com.github.rxyor.carp.ums.application.service.user.UserCmdService;
import com.github.rxyor.carp.ums.insfrastructure.controller.user.request.AllocRoleReq;
import com.github.rxyor.carp.ums.insfrastructure.controller.user.request.SaveUserReq;
import com.github.rxyor.carp.ums.insfrastructure.controller.user.request.SaveUserReqMapper;
import com.github.rxyor.carp.ums.insfrastructure.controller.user.request.UpdateUserReq;
import com.github.rxyor.carp.ums.insfrastructure.controller.user.request.UpdateUserReqMapper;
import com.github.rxyor.common.core.model.R;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@Validated
@Api(tags = "User")
@AllArgsConstructor
@RestController
@RequestMapping("/ums/user")
public class UserController {

    private final UserCmdService userCmdService;
    private final UserQryService userQryService;

    @ApiOperation("获取用户信息[id]")
    @GetMapping("/get")
    public R<UserDTO> get(
        @NotNull(message = "用户id不能为空")
        @RequestParam("id") Long id) {
        return R.success(userQryService.find(id));
    }

    @ApiOperation("获取用户信息[id]")
    @GetMapping("/get/with_roles")
    public R<UserRetDTO> getWithRoles(
        @NotNull(message = "用户id不能为空")
        @RequestParam("id") Long id) {
        return R.success(userQryService.findWithRoles(id));
    }

    @ApiOperation("分页查询")
    @PostMapping("/page")
    public R<Page<UserDTO>> page(@RequestBody UserQry req) {
        Preconditions.checkNotNull(req, "查询参数不能为空");

        Oauth2User user = AuthHolder.user();
        Object o = SecurityContextHolder.getContext().getAuthentication();
        Page<UserDTO> page = userQryService.page(req);
        return R.success(page);
    }

    @ApiOperation("保存用户")
    @PostMapping("/save")
    public R<String> save(@RequestBody SaveUserReq req) {
        Preconditions.checkNotNull(req, "请求参数不能为空");

        SaveUserCmd cmd = SaveUserReqMapper.INSTANCE.toSaveUserCmd(req);
        userCmdService.save(cmd);
        return R.success("ok");
    }

    @ApiOperation("更新用户")
    @PostMapping("/update")
    public R<String> update(@RequestBody UpdateUserReq req) {
        Preconditions.checkNotNull(req, "请求参数不能为空");

        UpdateUserCmd cmd = UpdateUserReqMapper.INSTANCE.toUpdateUserCmd(req);
        userCmdService.update(cmd);
        return R.success("ok");
    }

    @ApiOperation("启用用户")
    @PostMapping("/enable")
    public R<String> enable(
        @NotNull(message = "用户id不能为空")
        @RequestParam("id") Long id) {

        DisableUserCmd cmd = new DisableUserCmd();
        cmd.setId(id);
        cmd.setDisable(DisableEnum.ENABLE.getCode());

        userCmdService.disableOrEnable(cmd);
        return R.success("ok");
    }

    @ApiOperation("禁用用户")
    @PostMapping("/disable")
    public R<String> disable(
        @NotNull(message = "用户id不能为空")
        @RequestParam("id") Long id) {

        DisableUserCmd cmd = new DisableUserCmd();
        cmd.setId(id);
        cmd.setDisable(DisableEnum.DISABLE.getCode());

        userCmdService.disableOrEnable(cmd);
        return R.success("ok");
    }

    @ApiOperation("删除用户")
    @PostMapping("/delete")
    public R<Object> delete(
        @NotNull(message = "用户id不能为空")
        @RequestParam("id") Long id) {
        userCmdService.delete(id);
        return R.success("ok");
    }

    @ApiOperation("分配角色")
    @PostMapping("/alloc")
    public R<Object> alloc(@RequestBody AllocRoleReq req) {
        Preconditions.checkNotNull(req, "参数不能为空");

        AllocRoleCmd cmd = new AllocRoleCmd(req.getId(), req.getRoleIdList());
        userCmdService.allocRoles(cmd);
        return R.success("ok");
    }
}
