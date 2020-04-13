package com.github.rxyor.carp.common.rocketmq.listener;

import com.github.rxyor.carp.common.rocketmq.model.MqEvent;
import com.github.rxyor.carp.spring.boot.eventbus.listener.AbstractEventListener;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/4/9 周四 17:29:00
 * @since 1.0.0
 */
@Slf4j
@Component
public class TestEventListener extends AbstractEventListener {

    @Subscribe
    public void subMqEvent(MqEvent event) {
        log.info("Subscribe order event:{}", event);
    }
}
