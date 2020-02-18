package com.github.rxyor.carp.ums.insfrastructure.controller.role.request;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/19 周三 01:20:00
 * @since 1.0.0
 */
@Data
public class AllocPermissionReq {

    @ApiModelProperty("角色ID")
    private Long id;

    @ApiModelProperty("权限id列表")
    private List<Long> permissionIdList;

}
