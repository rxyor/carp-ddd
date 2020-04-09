package com.github.rxyor.carp.common.eventbus.consume;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
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
public class MqEventSubscriber<E extends IEvent> implements RocketMQListener<String> {

    private static ParserConfig config = new ParserConfig();

    static {
        config.setAutoTypeSupport(true);
    }

    @Override
    public void onMessage(String msg) {
        log.info("MqEventSubscriber receive msg:[{}]", msg);
        if (StringUtils.isBlank(msg)) {
            log.warn("MqEventSubscriber receive msg, but msg is blank");
            return;
        }

        try {
            Object data =  JSON.parse(msg,config);
            if(data instanceof IEvent){
                GuavaEventBus.send((IEvent) data);
            }else {
                log.error("MqEventSubscriber ignore not event type msg , context:[{}]",msg);
            }
        } catch (Exception e) {
            log.error("Msg:[] parse to event or send event fail, error:", msg, e);
        }
    }
}
