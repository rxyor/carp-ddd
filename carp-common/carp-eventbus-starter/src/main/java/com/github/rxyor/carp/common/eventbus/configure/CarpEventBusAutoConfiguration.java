package com.github.rxyor.carp.common.eventbus.configure;

import com.github.rxyor.carp.common.eventbus.produce.MqEventBus;
import com.github.rxyor.carp.common.eventbus.consume.MqEventSubscriber;
import javax.annotation.Resource;
import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties.Producer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQMessageConverter;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/4/9 周四 11:40:00
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(CarpEventBusProperties.class)
public class CarpEventBusAutoConfiguration {

    @Resource
    private CarpEventBusProperties carpEventBusProperties;

    @Resource
    private RocketMQProperties rocketMQProperties;

    @Resource
    private RocketMQMessageConverter rocketMQMessageConverter;

    @Bean
    public MqEventSubscriber mqEventSubscriber() {
        return new MqEventSubscriber();
    }

    @Bean
    public MqEventBus mqEventBus() {
        RocketMQTemplate rocketMQTemplate = new RocketMQTemplate();
        rocketMQTemplate.setProducer(mqProducer(rocketMQProperties));
        rocketMQTemplate.setMessageConverter(rocketMQMessageConverter.getMessageConverter());

        return new MqEventBus(rocketMQTemplate, carpEventBusProperties);
    }

    public DefaultMQProducer mqProducer(RocketMQProperties rocketMQProperties) {
        Producer producerConfig = rocketMQProperties.getProducer();
        String nameServer = rocketMQProperties.getNameServer();
        String groupName = producerConfig.getGroup();
        Assert.hasText(nameServer, "[rocketmq.name-server] must not be null");
        Assert.hasText(groupName, "[rocketmq.producer.group] must not be null");
        String accessChannel = rocketMQProperties.getAccessChannel();
        String ak = rocketMQProperties.getProducer().getAccessKey();
        String sk = rocketMQProperties.getProducer().getSecretKey();
        boolean isEnableMsgTrace = rocketMQProperties.getProducer().isEnableMsgTrace();
        String customizedTraceTopic = rocketMQProperties.getProducer().getCustomizedTraceTopic();
        DefaultMQProducer producer = RocketMQUtil
            .createDefaultMQProducer(groupName, ak, sk, isEnableMsgTrace, customizedTraceTopic);
        producer.setNamesrvAddr(nameServer);
        if (!StringUtils.isEmpty(accessChannel)) {
            producer.setAccessChannel(AccessChannel.valueOf(accessChannel));
        }

        producer.setSendMsgTimeout(producerConfig.getSendMessageTimeout());
        producer.setRetryTimesWhenSendFailed(producerConfig.getRetryTimesWhenSendFailed());
        producer.setRetryTimesWhenSendAsyncFailed(producerConfig.getRetryTimesWhenSendAsyncFailed());
        producer.setMaxMessageSize(producerConfig.getMaxMessageSize());
        producer.setCompressMsgBodyOverHowmuch(producerConfig.getCompressMessageBodyThreshold());
        producer.setRetryAnotherBrokerWhenNotStoreOK(producerConfig.isRetryNextServer());
        return producer;
    }

}
