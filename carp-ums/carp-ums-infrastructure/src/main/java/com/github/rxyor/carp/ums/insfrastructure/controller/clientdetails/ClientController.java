package com.github.rxyor.carp.ums.insfrastructure.controller.clientdetails;

import com.github.rxyor.carp.query.dto.clientdetails.ClientDTO;
import com.github.rxyor.carp.query.qry.clientdetails.ClientQry;
import com.github.rxyor.carp.query.service.clientdetails.ClientQryService;
import com.github.rxyor.carp.ums.application.command.clientdetails.SaveClientCmd;
import com.github.rxyor.carp.ums.application.command.clientdetails.UpdateClientCmd;
import com.github.rxyor.carp.ums.application.service.clientdetails.ClientCmdService;
import com.github.rxyor.carp.ums.insfrastructure.controller.clientdetails.request.SaveClientReq;
import com.github.rxyor.carp.ums.insfrastructure.controller.clientdetails.request.SaveClientReqMapper;
import com.github.rxyor.carp.ums.insfrastructure.controller.clientdetails.request.UpdateClientReq;
import com.github.rxyor.carp.ums.insfrastructure.controller.clientdetails.request.UpdateClientReqMapper;
import com.github.rxyor.common.core.model.R;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "Client")
@AllArgsConstructor
@RestController
@RequestMapping("/ums/client")
public class ClientController {

    private final ClientCmdService clientCmdService;
    private final ClientQryService clientQryService;

    @ApiOperation("获取客户端信息[id]")
    @GetMapping("/get")
    public R<ClientDTO> get(
        @NotNull(message = "客户端id不能为空")
        @RequestParam("id") Long id) {
        return R.success(clientQryService.find(id));
    }

    @ApiOperation("分页查询")
    @PostMapping("/page")
    public R<Page<ClientDTO>> page(@RequestBody ClientQry req) {
        Preconditions.checkNotNull(req, "查询参数不能为空");

        Page<ClientDTO> page = clientQryService.page(req);
        return R.success(page);
    }

    @ApiOperation("保存客户端")
    @PostMapping("/save")
    public R<String> save(@RequestBody SaveClientReq req) {
        Preconditions.checkNotNull(req, "请求参数不能为空");

        SaveClientCmd cmd = SaveClientReqMapper.INSTANCE
            .toSaveClientCmd(req);
        clientCmdService.save(cmd);
        return R.success("ok");
    }

    @ApiOperation("更新客户端")
    @PostMapping("/update")
    public R<String> update(@RequestBody UpdateClientReq req) {
        Preconditions.checkNotNull(req, "请求参数不能为空");

        UpdateClientCmd cmd = UpdateClientReqMapper.INSTANCE.toUpdateClientCmd(req);
        clientCmdService.update(cmd);
        return R.success("ok");
    }

    @ApiOperation("删除客户端")
    @PostMapping("/delete")
    public R<Object> delete(
        @NotNull(message = "客户端id不能为空")
        @RequestParam("id") Long id) {
        clientCmdService.delete(id);
        return R.success("ok");
    }
}
