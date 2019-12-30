package com.github.rxyor.carp.ums.api.enums.common;

import com.github.rxyor.common.core.enums.KeyValue;
import lombok.Getter;
import lombok.ToString;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 15:58:00
 * @since 1.0.0
 */
@ToString
public enum DisableEnum implements KeyValue<Integer> {

    DISABLE(1, "禁用"),
    ENABLE(1, "禁用"),
    ;
    @Getter
    private Integer code;

    @Getter
    private String desc;

    DisableEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
