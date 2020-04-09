package com.github.rxyor.carp.common.eventbus.consume;

import com.github.rxyor.carp.common.eventbus.configure.CarpRmqEventListener;
import com.github.rxyor.carp.common.eventbus.core.IEvent;
import org.apache.rocketmq.spring.core.RocketMQListener;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/4/9 周四 14:06:00
 * @since 1.0.0
 */
@CarpRmqEventListener
public class MqEventSubscriber<E extends IEvent> implements RocketMQListener<String> {

    @Override
    public void onMessage(String e) {
        System.out.println("receive event: " + e);
    }
}
