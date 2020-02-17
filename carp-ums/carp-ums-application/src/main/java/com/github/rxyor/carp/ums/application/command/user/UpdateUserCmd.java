package com.github.rxyor.carp.ums.application.command.user;

import javax.validation.constraints.Email;
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
public class UpdateUserCmd {

    @NotNull(message = "id不能为空")
    private Long id;

    private String username;

    @Pattern(regexp = "(^[0-9a-zA-Z.]{6,18}$)|(^[\\s]{0}$)", message = "密码必须是数字或字母或英文句号, 且6~18位")
    private String password;

    @Length(min = 11, max = 11, message = "手机号长度必须是{max}位")
    private String phone;

    @Email(message = "邮箱格式错误")
    private String email;

    private String nickname;

    private String avatar;

    private Integer disable;

    private Integer locked;

    private String remark;
}
