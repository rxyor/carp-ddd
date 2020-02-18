package com.github.rxyor.carp.ums.application.command.permission;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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

    @NotBlank(message = "权限编码不能为空")
    @Pattern(regexp = "^[0-9z-zA-Z-_]{4,50}$",message = "权限编码由4~50位数字、字母、-_组成")
    private String permissionCode;

    @NotBlank(message = "权限名称不能为空")
    @Length(min = 2, max = 50, message = "权限名称有效长度[{min}~{max}]")
    private String permissionName;

    @Length(min = 2, max = 50, message = "备注有效长度[{min}~{max}]")
    private String remark;
}
