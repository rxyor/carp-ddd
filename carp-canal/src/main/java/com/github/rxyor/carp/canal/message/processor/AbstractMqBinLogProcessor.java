package com.github.rxyor.carp.canal.message.processor;

import com.github.rxyor.carp.canal.message.consume.MqBinLogReceiver;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

@Slf4j
public abstract class AbstractMqBinLogProcessor<T> implements MqBinLogProcessor<T>, InitializingBean {

    @Resource
    protected MqBinLogReceiver mqBinLogReceiver;

    public void afterPropertiesSet() throws Exception {
        boolean ret = mqBinLogReceiver.register(this);
        if (!ret) {
            log.error("MqBinLogProcessor:[{}] register failed!", this.getClass().getName());
        }
    }
}
