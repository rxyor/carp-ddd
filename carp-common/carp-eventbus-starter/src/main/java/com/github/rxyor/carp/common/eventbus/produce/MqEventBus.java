package com.github.rxyor.carp.common.eventbus.produce;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.rxyor.carp.common.eventbus.configure.CarpEventBusProperties;
import com.github.rxyor.carp.common.eventbus.core.IEvent;
import com.github.rxyor.carp.common.eventbus.listener.BusRegister;
import com.github.rxyor.carp.common.eventbus.listener.IEventListener;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/4/9 周四 15:15:00
 * @since 1.0.0
 */
@Slf4j
public class MqEventBus<E extends IEvent> extends BusRegister implements IEventBus<E> {

    private static MqEventBus<? extends IEvent> INSTANCE;

    private final RocketMQTemplate rocketMQTemplate;

    private final CarpEventBusProperties carpEventBusProperties;

    public MqEventBus(RocketMQTemplate rocketMQTemplate,
        CarpEventBusProperties carpEventBusProperties) {
        Objects.requireNonNull(rocketMQTemplate, "rocketMQTemplate can't be null!");
        Objects.requireNonNull(carpEventBusProperties, "carpEventBusProperties can't be null!");

        this.rocketMQTemplate = rocketMQTemplate;
        this.carpEventBusProperties = carpEventBusProperties;
        MqEventBus.INSTANCE = this;
    }

    public static <E extends IEvent> void send(E event) {
        Objects.requireNonNull(INSTANCE, "MqEventBus instance hasn't be init");

        INSTANCE.post(event);
    }

    public static void registerListener(IEventListener listener) {
        Objects.requireNonNull(INSTANCE, "MqEventBus instance hasn't be init");

        INSTANCE.register(listener);
    }

    public static void unregisterListener(IEventListener listener) {
        Objects.requireNonNull(INSTANCE, "MqEventBus instance hasn't be init");

        INSTANCE.unregister(listener);
    }

    @Override
    public void post(IEvent event) {
        Objects.requireNonNull(event, "event can't be null");

        String content = JSON.toJSONString(event, new SerializerFeature[]{SerializerFeature.WriteClassName});
        if (log.isDebugEnabled()) {
            log.debug("[{}] send event, content:[{}]", this.getClass().getName(), content);
        }

        rocketMQTemplate.asyncSendOrderly(carpEventBusProperties.getTopic(), content, event.getEventKey(),
            new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("[{}] send event success, content:[{}], send result[{}]:",
                        this.getClass().getName(), content, sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    log.error("[{}] send event fail, content:[{}], error:",
                        this.getClass().getName(), content, throwable);
                }
            });
    }
}
