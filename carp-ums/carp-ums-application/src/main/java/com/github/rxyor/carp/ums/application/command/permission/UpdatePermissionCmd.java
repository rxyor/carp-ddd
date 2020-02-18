package com.github.rxyor.carp.ums.application.command.permission;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

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
public class UpdatePermissionCmd {

    @NotNull(message = "id不能为空")
    private Long id;

    @Pattern(regexp = "(^[0-9z-zA-Z-_]{4,50}$)|(^[\\s]{0}$)", message = "权限编码由4~50位数字、字母、-_组成")
    private String permissionCode;

    @Length(min = 2, max = 50, message = "权限名称有效长度[{min}~{max}]")
    private String permissionName;

    @Length(min = 2, max = 50, message = "备注有效长度[{min}~{max}]")
    private String remark;
}
