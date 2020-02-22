package com.github.rxyor.carp.auth.common.enums;

import com.github.rxyor.common.core.enums.KeyValue;
import lombok.Getter;
import lombok.ToString;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/22 周六 21:58:00
 * @since 1.0.0
 */
@ToString(callSuper = true)
public enum BizExCodeEnum implements KeyValue<Integer> {

    USERNAME_PASSWORD_ERROR(5401, "用户名或者密码错误"),
    CAPTCHA_ERROR(5402, "验证码错误"),
    LOGIN_TIMEOUT(5411, "登录超时"),
    ;

    @Getter
    private Integer code;

    @Getter
    private String desc;

    BizExCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
