package com.github.rxyor.carp.ums.insfrastructure.controller.permission;

import com.github.rxyor.carp.query.dto.permission.PermissionDTO;
import com.github.rxyor.carp.query.qry.permission.PermissionQry;
import com.github.rxyor.carp.query.service.permission.PermissionQryService;
import com.github.rxyor.carp.ums.api.enums.common.DisableEnum;
import com.github.rxyor.carp.ums.application.command.permission.DisablePermissionCmd;
import com.github.rxyor.carp.ums.application.command.permission.SavePermissionCmd;
import com.github.rxyor.carp.ums.application.command.permission.UpdatePermissionCmd;
import com.github.rxyor.carp.ums.application.service.permission.PermissionCmdService;
import com.github.rxyor.carp.ums.insfrastructure.controller.permission.request.SavePermissionReq;
import com.github.rxyor.carp.ums.insfrastructure.controller.permission.request.SavePermissionReqMapper;
import com.github.rxyor.carp.ums.insfrastructure.controller.permission.request.UpdatePermissionReq;
import com.github.rxyor.carp.ums.insfrastructure.controller.permission.request.UpdatePermissionReqMapper;
import com.github.rxyor.common.core.model.R;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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
@Api(tags = "Permission")
@AllArgsConstructor
@RestController
@RequestMapping("/ums/permission")
public class PermissionController {

    private final PermissionCmdService permissionCmdService;
    private final PermissionQryService permissionQryService;

    @ApiOperation("获取权限信息[id]")
    @GetMapping("/get")
    public R<PermissionDTO> get(
        @NotNull(message = "权限id不能为空")
        @RequestParam("id") Long id) {
        return R.success(permissionQryService.find(id));
    }

    @ApiOperation("分页查询")
    @PostMapping("/page")
    public R<Page<PermissionDTO>> page(@RequestBody PermissionQry req) {
        Preconditions.checkNotNull(req, "查询参数不能为空");

        Page<PermissionDTO> page = permissionQryService.page(req);
        return R.success(page);
    }

    @ApiOperation("查询所有启用")
    @GetMapping("/list/enable")
    public R<List<PermissionDTO>> listEnable() {
        List<PermissionDTO> list = permissionQryService.listEnable();
        return R.success(list);
    }

    @ApiOperation("保存权限")
    @PostMapping("/save")
    public R<Object> save(
        @RequestBody SavePermissionReq req) {
        Preconditions.checkNotNull(req, "请求参数不能为空");

        SavePermissionCmd cmd = SavePermissionReqMapper.INSTANCE.toSavePermissionCmd(req);
        permissionCmdService.save(cmd);
        return R.success("ok");
    }

    @ApiOperation("更新权限")
    @PostMapping("/update")
    public R<String> update(@RequestBody UpdatePermissionReq req) {
        Preconditions.checkNotNull(req, "请求参数不能为空");

        UpdatePermissionCmd cmd = UpdatePermissionReqMapper.INSTANCE.toUpdatePermissionCmd(req);
        permissionCmdService.update(cmd);
        return R.success("ok");
    }

    @ApiOperation("启用权限")
    @PostMapping("/enable")
    public R<String> enable(
        @NotNull(message = "权限id不能为空")
        @RequestParam("id") Long id) {

        DisablePermissionCmd cmd = new DisablePermissionCmd();
        cmd.setId(id);
        cmd.setDisable(DisableEnum.ENABLE.getCode());

        permissionCmdService.disableOrEnable(cmd);
        return R.success("ok");
    }

    @ApiOperation("禁用权限")
    @PostMapping("/disable")
    public R<String> disable(
        @NotNull(message = "权限id不能为空")
        @RequestParam("id") Long id) {

        DisablePermissionCmd cmd = new DisablePermissionCmd();
        cmd.setId(id);
        cmd.setDisable(DisableEnum.DISABLE.getCode());

        permissionCmdService.disableOrEnable(cmd);
        return R.success("ok");
    }

    @ApiOperation("禁用权限")
    @PostMapping("/delete")
    public R<Object> delete(
        @NotNull(message = "权限id不能为空")
        @RequestParam("id") Long id) {
        permissionCmdService.delete(id);
        return R.success("ok");
    }
}
