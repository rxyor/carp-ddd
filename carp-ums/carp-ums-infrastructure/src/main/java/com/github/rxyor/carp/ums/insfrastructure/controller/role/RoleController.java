package com.github.rxyor.carp.ums.insfrastructure.controller.role;

import com.github.rxyor.carp.query.dto.role.RoleDTO;
import com.github.rxyor.carp.query.qry.role.RoleQry;
import com.github.rxyor.carp.query.service.role.RoleQryService;
import com.github.rxyor.carp.ums.api.dto.ums.RoleRetDTO;
import com.github.rxyor.carp.ums.api.enums.common.DisableEnum;
import com.github.rxyor.carp.ums.application.command.role.AllocPermissionCmd;
import com.github.rxyor.carp.ums.application.command.role.DisableRoleCmd;
import com.github.rxyor.carp.ums.application.command.role.SaveRoleCmd;
import com.github.rxyor.carp.ums.application.command.role.UpdateRoleCmd;
import com.github.rxyor.carp.ums.application.service.role.RoleCmdService;
import com.github.rxyor.carp.ums.insfrastructure.controller.role.request.AllocPermissionReq;
import com.github.rxyor.carp.ums.insfrastructure.controller.role.request.SaveRoleReq;
import com.github.rxyor.carp.ums.insfrastructure.controller.role.request.SaveRoleReqMapper;
import com.github.rxyor.carp.ums.insfrastructure.controller.role.request.UpdateRoleReq;
import com.github.rxyor.carp.ums.insfrastructure.controller.role.request.UpdateRoleReqMapper;
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
@Api(tags = "Role")
@AllArgsConstructor
@RestController
@RequestMapping("/ums/role")
public class RoleController {

    private final RoleCmdService roleCmdService;
    private final RoleQryService roleQryService;

    @ApiOperation("获取角色信息[id]")
    @GetMapping("/get")
    public R<RoleDTO> get(
        @NotNull(message = "角色id不能为空")
        @RequestParam("id") Long id) {
        return R.success(roleQryService.find(id));
    }

    @ApiOperation("获取角色信息[id]")
    @GetMapping("/get/with_permissions")
    public R<RoleRetDTO> getWithPermissions(
        @NotNull(message = "角色id不能为空")
        @RequestParam("id") Long id) {
        return R.success(roleQryService.findWithPermissions(id));
    }

    @ApiOperation("查询所有启用")
    @GetMapping("/list/enable")
    public R<List<RoleDTO>> listEnable() {
        List<RoleDTO> list = roleQryService.listEnable();
        return R.success(list);
    }

    @ApiOperation("分页查询")
    @PostMapping("/page")
    public R<Page<RoleDTO>> page(@RequestBody RoleQry req) {
        Preconditions.checkNotNull(req, "查询参数不能为空");

        Page<RoleDTO> page = roleQryService.page(req);
        return R.success(page);
    }

    @ApiOperation("保存角色")
    @PostMapping("/save")
    public R<Object> save(
        @RequestBody SaveRoleReq req) {
        Preconditions.checkNotNull(req, "请求参数不能为空");

        SaveRoleCmd cmd = SaveRoleReqMapper.INSTANCE.toSaveRoleCmd(req);
        roleCmdService.save(cmd);
        return R.success("ok");
    }

    @ApiOperation("更新角色")
    @PostMapping("/update")
    public R<String> update(@RequestBody UpdateRoleReq req) {
        Preconditions.checkNotNull(req, "请求参数不能为空");

        UpdateRoleCmd cmd = UpdateRoleReqMapper.INSTANCE.toUpdateRoleCmd(req);
        roleCmdService.update(cmd);
        return R.success("ok");
    }

    @ApiOperation("启用角色")
    @PostMapping("/enable")
    public R<String> enable(
        @NotNull(message = "角色id不能为空")
        @RequestParam("id") Long id) {

        DisableRoleCmd cmd = new DisableRoleCmd();
        cmd.setId(id);
        cmd.setDisable(DisableEnum.ENABLE.getCode());

        roleCmdService.disableOrEnable(cmd);
        return R.success("ok");
    }

    @ApiOperation("禁用角色")
    @PostMapping("/disable")
    public R<String> disable(
        @NotNull(message = "角色id不能为空")
        @RequestParam("id") Long id) {

        DisableRoleCmd cmd = new DisableRoleCmd();
        cmd.setId(id);
        cmd.setDisable(DisableEnum.DISABLE.getCode());

        roleCmdService.disableOrEnable(cmd);
        return R.success("ok");
    }

    @ApiOperation("禁用角色")
    @PostMapping("/delete")
    public R<Object> delete(
        @NotNull(message = "角色id不能为空")
        @RequestParam("id") Long id) {
        roleCmdService.delete(id);
        return R.success("ok");
    }

    @ApiOperation("分配权限")
    @PostMapping("/alloc")
    public R<Object> alloc(@RequestBody AllocPermissionReq req) {
        Preconditions.checkNotNull(req, "参数不能为空");

        AllocPermissionCmd cmd = new AllocPermissionCmd(
            req.getId(), req.getPermissionIdList());
        roleCmdService.allocPermissions(cmd);
        return R.success("ok");
    }
}
