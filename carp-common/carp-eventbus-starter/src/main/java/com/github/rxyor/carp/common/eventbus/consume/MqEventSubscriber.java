package com.github.rxyor.carp.common.eventbus.consume;

import com.alibaba.fastjson.JSON;
import com.github.rxyor.carp.common.eventbus.configure.CarpRmqEventListener;
import com.github.rxyor.carp.common.eventbus.core.IEvent;
import com.github.rxyor.carp.common.eventbus.produce.GuavaEventBus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Slf4j
@CarpRmqEventListener
public class MqEventSubscriber<E extends IEvent> implements RocketMQListener<String> {

    @Override
    public void onMessage(String msg) {
        log.info("MqEventSubscriber receive msg:[{}]", msg);
        if (StringUtils.isBlank(msg)) {
            log.warn("MqEventSubscriber receive msg, but msg is blank");
            return;
        }

        try {
            IEvent event = (IEvent) JSON.parseObject(msg);
            GuavaEventBus.send(event);
        } catch (Exception e) {
            log.error("Msg:[] parse to event or send event fail, error:", msg, e);
        }
    }
}
