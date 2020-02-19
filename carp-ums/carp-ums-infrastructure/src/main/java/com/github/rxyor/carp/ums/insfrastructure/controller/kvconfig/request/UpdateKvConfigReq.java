package com.github.rxyor.carp.ums.insfrastructure.controller.kvconfig.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/17 周一 23:11:00
 * @since 1.0.0
 */
@Data
public class UpdateKvConfigReq {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("键")
    private String key;

    @ApiModelProperty("值")
    private String value;

    @ApiModelProperty("描述说明")
    private String desc;

    @ApiModelProperty("应用标识")
    private String appId;
}
