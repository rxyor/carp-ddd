package com.github.rxyor.carp.common.eventbus.core;

import java.util.UUID;

/**
 *<p>
 * 抽象事件接口
 *</p>
 *
 * @author liuyang
 * @date 2020/4/9 周四 14:07:00
 * @since 1.0.0
 */
public interface IEvent {

    static String uuid() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    String getEventKey();
}
