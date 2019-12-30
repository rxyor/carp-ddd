package com.github.rxyor.carp.ums.application.command.role;

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
public class SaveRoleCmd {

    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空")
    @Length(min = 4,max = 50,message = "角色编码有效长度[{min}~{max}]")
    private String roleCode;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Length(min = 2,max = 50,message = "角色名称有效长度[{min}~{max}]")
    private String roleName;

    /**
     * 角色描述
     */
    @NotBlank(message = "备注不能为空")
    @Length(min = 2,max = 50,message = "备注有效长度[{min}~{max}]")
    private String remark;
}