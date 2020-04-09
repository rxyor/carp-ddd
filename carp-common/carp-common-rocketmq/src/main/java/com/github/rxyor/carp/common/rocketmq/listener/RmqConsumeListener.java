package com.github.rxyor.carp.common.rocketmq.listener;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/4/9 周四 00:39:00
 * @since 1.0.0
 */
@Component
@RocketMQMessageListener(topic = "Topic_carp_rocketmq", consumerGroup = "GID_carp_rocketmq")
public class RmqConsumeListener implements RocketMQListener<Object> {

    @Override
    public void onMessage(Object o) {
        System.out.println("receive: "+o);
    }
}
