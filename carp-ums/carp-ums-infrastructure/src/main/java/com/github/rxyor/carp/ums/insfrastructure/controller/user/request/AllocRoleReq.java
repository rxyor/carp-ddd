package com.github.rxyor.carp.ums.insfrastructure.controller.user.request;

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
public class AllocRoleReq {

    @ApiModelProperty("用户ID")
    private Long id;

    @ApiModelProperty("角色ID列表")
    private List<Long> roleIdList;

}
