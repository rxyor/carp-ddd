package com.github.rxyor.carp.ums.application.command.user;

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
 * @date 2019/12/27 周五 23:11:00
 * @since 1.0.0
 */
@Data
public class SaveUserCmd {

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^[0-9a-zA-Z.]{6,18}$",message = "密码必须是数字或字母或英文句号, 且6~18位")
    private String password;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11,max = 11,message = "手机号长度必须是{max}位")
    private String phone;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    /**
     * 随机盐
     */
    private String salt;

    /**
     * 备注
     */
    private String remark;
}
