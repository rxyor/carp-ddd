package com.github.rxyor.carp.ums.application.command.role;

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
public class UpdateRoleCmd {

    @NotNull(message = "id不能为空")
    private Long id;

    @Pattern(regexp = "(^[0-9z-zA-Z-_]{4,50}$)|(^[\\s]{0}$)", message = "角色编码由4~50位数字、字母、-_组成")
    private String roleCode;

    @Length(min = 2, max = 50, message = "角色名称有效长度[{min}~{max}]")
    private String roleName;

    @Length(min = 2, max = 50, message = "备注有效长度[{min}~{max}]")
    private String remark;
}
