package com.github.rxyor.carp.ddd.application.command.permission;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/28 周六 17:05:00
 * @since 1.0.0
 */
@Data
public class SavePermissionCmd {

    /**
     * 权限编码
     */
    @NotBlank(message = "权限编码不能为空")
    @Length(min = 4, max = 50, message = "权限编码有效长度[{min}~{max}]")
    private String permissionCode;

    /**
     * 权限名称
     */
    @NotBlank(message = "权限名称不能为空")
    @Length(min = 2, max = 50, message = "权限名称有效长度[{min}~{max}]")
    private String permissionName;

    /**
     * 权限描述
     */
    @NotBlank(message = "备注不能为空")
    @Length(min = 2, max = 50, message = "备注有效长度[{min}~{max}]")
    private String remark;
}
