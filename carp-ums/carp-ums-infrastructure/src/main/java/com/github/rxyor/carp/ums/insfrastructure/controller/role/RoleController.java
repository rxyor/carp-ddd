package com.github.rxyor.carp.ums.insfrastructure.controller.role;

import com.github.rxyor.carp.ums.application.command.role.SaveRoleCmd;
import com.github.rxyor.carp.ums.application.service.role.RoleCmdService;
import com.github.rxyor.carp.ums.insfrastructure.controller.role.request.SaveRoleReq;
import com.github.rxyor.carp.ums.insfrastructure.controller.role.request.SaveRoleReqMapper;
import com.github.rxyor.common.core.model.R;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
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
@Api(tags = "角色")
@AllArgsConstructor
@RestController
@RequestMapping("/ums/role")
public class RoleController {

    private final RoleCmdService roleCmdService;

    @ApiOperation("保存角色")
    @PostMapping("/save")
    public R<Object> save(
        @RequestBody SaveRoleReq req) {
        Preconditions.checkNotNull(req, "请求参数不能为空");

        SaveRoleCmd cmd = SaveRoleReqMapper.INSTANCE.toSaveRoleCmd(req);
        roleCmdService.save(cmd);
        return R.success("ok");
    }
}
