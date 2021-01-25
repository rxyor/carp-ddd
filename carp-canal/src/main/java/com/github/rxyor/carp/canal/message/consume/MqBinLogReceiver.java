package com.github.rxyor.carp.canal.message.consume;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import com.github.rxyor.carp.canal.common.model.MqBinLog;
import com.github.rxyor.carp.canal.message.processor.MqBinLogProcessor;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(
    consumerGroup = "${canal.rocketmq.consume.group.test}",
    topic = "${canal.rocketmq.consume.topic.test}",
    consumeMode = ConsumeMode.ORDERLY)
public class MqBinLogReceiver implements RocketMQListener<String> {

    private final Map<String, MqBinLogProcessor<?>> processors = new ConcurrentHashMap<>();



    @Override
    public void onMessage(String data) {
        if (StringUtils.isBlank(data)) {
            return;
        }

        MqBinLog<String> o = JSON.parseObject(data, new TypeReference<MqBinLog<String>>() {
        });

        processors.forEach((s, processor) -> {
            if (processor.accept(o.getDatabase(), o.getTable())) {
                processor.process(o);
            }
        });
    }

    public boolean register(MqBinLogProcessor<?> processor) {
        if (processor == null) {
            return false;
        }
        processors.put(processor.getClass().getName(), processor);
        return true;
    }
}
