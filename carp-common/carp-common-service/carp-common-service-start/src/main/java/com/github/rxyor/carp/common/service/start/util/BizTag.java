package com.github.rxyor.carp.common.service.start.util;

import com.github.rxyor.common.core.enums.KeyValue;
import lombok.Getter;
import lombok.ToString;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/4/26 周日 15:55:00
 * @since 1.0.0
 */
@ToString
public enum BizTag implements KeyValue<String> {
    TEST("leaf-segment-test", "测试"),
    ;

    @Getter
    private final String code;

    @Getter
    private final String desc;

    BizTag(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
