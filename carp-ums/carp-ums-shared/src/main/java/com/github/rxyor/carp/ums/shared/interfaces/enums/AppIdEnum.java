package com.github.rxyor.carp.ums.shared.interfaces.enums;

import com.github.rxyor.common.core.enums.KeyValue;
import lombok.Getter;
import lombok.ToString;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/19 周三 15:45:00
 * @since 1.0.0
 */
@ToString
public enum AppIdEnum implements KeyValue<String> {

    GLOBAL("GLOBAL", "全局"),
    UMS("UMS", "用户权限服务"),
    AUTH("AUTH", "登录授权服务"),
    ;

    @Getter
    private String code;

    @Getter
    private String desc;

    AppIdEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
