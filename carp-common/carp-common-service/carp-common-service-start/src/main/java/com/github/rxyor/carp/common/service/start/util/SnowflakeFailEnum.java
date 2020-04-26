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
public enum SnowflakeFailEnum implements KeyValue<Long> {
    CLOCK_SLOW_AND_WAIT_FAIL(-1L, "服务系统时钟慢, 等待后仍然慢"),
    CLOCK_SLOW_AND_WAIT_INTERRUPTED(-2L, "服务系统时钟慢, 等待中断异常"),
    CLOCK_SLOW_TOO_MUCH(-3L, "服务节点系统时钟不同步, 差值太大"),
    ;

    @Getter
    private final Long code;

    @Getter
    private final String desc;

    SnowflakeFailEnum(Long code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
