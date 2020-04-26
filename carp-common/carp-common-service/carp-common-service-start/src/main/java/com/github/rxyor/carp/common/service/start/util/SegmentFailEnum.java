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
public enum SegmentFailEnum implements KeyValue<Long> {
    EXCEPTION_ID_CACHE_INIT_FALSE(-1L, "IDCache未初始化成功时的异常码"),
    EXCEPTION_ID_KEY_NOT_EXISTS(-2L, "key不存在时的异常码"),
    EXCEPTION_ID_TWO_SEGMENTS_ARE_NULL(-3L, "SegmentBuffer中的两个Segment均未从DB中装载时的异常码"),
    ;

    @Getter
    private final Long code;

    @Getter
    private final String desc;

    SegmentFailEnum(Long code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
