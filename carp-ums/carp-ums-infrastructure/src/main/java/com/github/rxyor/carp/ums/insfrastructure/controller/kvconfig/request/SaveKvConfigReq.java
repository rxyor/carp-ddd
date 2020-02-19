package com.github.rxyor.carp.ums.insfrastructure.controller.kvconfig.request;

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
public class SaveKvConfigReq {

    @ApiModelProperty("键")
    private String key;

    @ApiModelProperty("值")
    private String value;

    @ApiModelProperty("描述说明")
    private String desc;

    @ApiModelProperty("应用标识")
    private String appId;

}
