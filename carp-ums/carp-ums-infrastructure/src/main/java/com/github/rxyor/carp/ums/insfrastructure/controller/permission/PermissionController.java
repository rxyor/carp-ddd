package com.github.rxyor.carp.ums.insfrastructure.controller.permission;

import com.github.rxyor.carp.ums.application.command.permission.SavePermissionCmd;
import com.github.rxyor.carp.ums.application.service.permission.PermissionCmdService;
import com.github.rxyor.carp.ums.insfrastructure.controller.permission.request.SavePermissionReq;
import com.github.rxyor.carp.ums.insfrastructure.controller.permission.request.SavePermissionReqMapper;
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
@Api(tags = "权限")
@AllArgsConstructor
@RestController
@RequestMapping("/ums/permission")
public class PermissionController {

    private final PermissionCmdService permissionCmdService;

    @ApiOperation("保存用户")
    @PostMapping("/save")
    public R<Object> save(
        @RequestBody SavePermissionReq req) {
        Preconditions.checkNotNull(req, "请求参数不能为空");

        SavePermissionCmd cmd = SavePermissionReqMapper.INSTANCE.toSavePermissionCmd(req);
        permissionCmdService.save(cmd);
        return R.success("ok");
    }
}
