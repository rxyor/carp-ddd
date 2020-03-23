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
 * @date 2020/3/23 周一 20:59:00
 * @since 1.0.0
 */
@ToString(callSuper = true)
public enum YesNoEnum implements KeyValue<Integer> {

    Y(1, "是"),
    N(0, "否"),
    ;
    @Getter
    private Integer code;

    @Getter
    private String desc;

    YesNoEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}