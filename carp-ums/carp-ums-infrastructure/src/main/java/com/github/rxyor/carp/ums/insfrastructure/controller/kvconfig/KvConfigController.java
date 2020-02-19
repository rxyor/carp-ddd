package com.github.rxyor.carp.ums.insfrastructure.controller.kvconfig;

import com.github.rxyor.carp.query.dto.kvconfig.KvConfigDTO;
import com.github.rxyor.carp.query.qry.kvconfig.KvConfigQry;
import com.github.rxyor.carp.query.service.kvconfig.KvConfigQryService;
import com.github.rxyor.carp.ums.application.command.kvconfig.SaveKvConfigCmd;
import com.github.rxyor.carp.ums.application.command.kvconfig.UpdateKvConfigCmd;
import com.github.rxyor.carp.ums.application.service.kvconfig.KvConfigCmdService;
import com.github.rxyor.carp.ums.insfrastructure.controller.kvconfig.request.SaveKvConfigReq;
import com.github.rxyor.carp.ums.insfrastructure.controller.kvconfig.request.SaveKvConfigReqMapper;
import com.github.rxyor.carp.ums.insfrastructure.controller.kvconfig.request.UpdateKvConfigReq;
import com.github.rxyor.carp.ums.insfrastructure.controller.kvconfig.request.UpdateKvConfigReqMapper;
import com.github.rxyor.carp.ums.shared.interfaces.enums.AppIdEnum;
import com.github.rxyor.common.core.model.R;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
@Api(tags = "KvConfig")
@AllArgsConstructor
@RestController
@RequestMapping("/ums/kvconfig")
public class KvConfigController {

    private final KvConfigCmdService kvConfigCmdService;
    private final KvConfigQryService kvConfigQryService;

    @ApiOperation("获取配置信息[id]")
    @GetMapping("/get")
    public R<KvConfigDTO> get(
        @NotNull(message = "配置id不能为空")
        @RequestParam("id") Long id) {
        return R.success(kvConfigQryService.find(id));
    }

    @ApiOperation("分页查询")
    @PostMapping("/page")
    public R<Page<KvConfigDTO>> page(@RequestBody KvConfigQry req) {
        Preconditions.checkNotNull(req, "查询参数不能为空");

        Page<KvConfigDTO> page = kvConfigQryService.page(req);
        return R.success(page);
    }

    @ApiOperation("保存配置")
    @PostMapping("/save")
    public R<String> save(@RequestBody SaveKvConfigReq req) {
        Preconditions.checkNotNull(req, "请求参数不能为空");

        SaveKvConfigCmd cmd = SaveKvConfigReqMapper.INSTANCE
            .toSaveKvConfigCmd(req);
        if (StringUtils.isBlank(cmd.getAppId())) {
            cmd.setAppId(AppIdEnum.GLOBAL.getCode());
        }
        kvConfigCmdService.save(cmd);
        return R.success("ok");
    }

    @ApiOperation("更新配置")
    @PostMapping("/update")
    public R<String> update(@RequestBody UpdateKvConfigReq req) {
        Preconditions.checkNotNull(req, "请求参数不能为空");

        UpdateKvConfigCmd cmd = UpdateKvConfigReqMapper.INSTANCE
            .toUpdateKvConfigCmd(req);
        kvConfigCmdService.update(cmd);
        return R.success("ok");
    }

    @ApiOperation("删除配置")
    @PostMapping("/delete")
    public R<Object> delete(
        @NotNull(message = "配置id不能为空")
        @RequestParam("id") Long id) {
        kvConfigCmdService.delete(id);
        return R.success("ok");
    }
}
