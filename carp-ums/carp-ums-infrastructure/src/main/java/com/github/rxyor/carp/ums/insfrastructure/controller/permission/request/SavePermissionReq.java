package com.github.rxyor.carp.ums.insfrastructure.controller.permission.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

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
public class SavePermissionReq {

    /**
     * 权限编码
     */
    @ApiModelProperty("权限编码")
    @NotBlank(message = "权限编码不能为空")
    @Length(min = 4, max = 50, message = "权限编码有效长度[{min}~{max}]")
    private String permissionCode;

    /**
     * 权限名称
     */
    @ApiModelProperty("权限名称")
    @NotBlank(message = "权限名称不能为空")
    @Length(min = 2, max = 50, message = "权限名称有效长度[{min}~{max}]")
    private String permissionName;

    /**
     * 权限描述
     */
    @ApiModelProperty("权限描述")
    @NotBlank(message = "备注不能为空")
    @Length(min = 2, max = 50, message = "备注有效长度[{min}~{max}]")
    private String remark;
}
