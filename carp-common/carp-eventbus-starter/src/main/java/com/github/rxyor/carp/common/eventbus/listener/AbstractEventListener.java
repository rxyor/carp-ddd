package com.github.rxyor.carp.common.eventbus.listener;

import com.github.rxyor.carp.common.eventbus.produce.GuavaAsyncEventBus;
import com.github.rxyor.carp.common.eventbus.produce.GuavaEventBus;
import com.github.rxyor.carp.common.eventbus.produce.MqEventBus;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/4/9 周四 16:09:00
 * @since 1.0.0
 */
public class AbstractEventListener implements IEventListener {

    @Autowired
    private MqEventBus mqEventBus;

    @Override
    public void destroy() throws Exception {
        GuavaEventBus.registerListener(this);
        GuavaAsyncEventBus.registerListener(this);
        MqEventBus.unregisterListener(this);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        GuavaEventBus.unregisterListener(this);
        GuavaAsyncEventBus.unregisterListener(this);
        MqEventBus.registerListener(this);
    }
}
