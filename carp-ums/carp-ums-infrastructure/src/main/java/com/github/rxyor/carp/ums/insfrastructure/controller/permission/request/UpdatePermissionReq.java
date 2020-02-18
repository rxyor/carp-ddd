package com.github.rxyor.carp.ums.insfrastructure.controller.permission.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/27 周五 23:34:00
 * @since 1.0.0
 */
@ApiModel
@Data
public class UpdatePermissionReq {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("角色编码")
    private String permissionCode;

    @ApiModelProperty("角色名称")
    private String permissionName;

    @ApiModelProperty("角色描述")
    private String remark;
}
