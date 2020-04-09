package com.github.rxyor.carp.common.eventbus.produce;

import com.github.rxyor.carp.common.eventbus.core.IEvent;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/10/25 周五 10:07:00
 * @since 1.0.0
 */
public interface IEventBus<E extends IEvent> {

    void post(E event);
}
