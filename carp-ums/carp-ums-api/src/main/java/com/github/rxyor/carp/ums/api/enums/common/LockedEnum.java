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
public enum LockedEnum implements KeyValue<Integer> {

    LOCKED(1, "锁定"),
    UNLOCK(0, "未锁定"),
    ;
    @Getter
    private Integer code;

    @Getter
    private String desc;

    LockedEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
