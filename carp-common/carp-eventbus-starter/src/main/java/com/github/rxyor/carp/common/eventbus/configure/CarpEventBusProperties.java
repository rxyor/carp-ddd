package com.github.rxyor.carp.common.eventbus.configure;

import static org.apache.rocketmq.spring.annotation.RocketMQMessageListener.ACCESS_CHANNEL_PLACEHOLDER;
import static org.apache.rocketmq.spring.annotation.RocketMQMessageListener.ACCESS_KEY_PLACEHOLDER;
import static org.apache.rocketmq.spring.annotation.RocketMQMessageListener.NAME_SERVER_PLACEHOLDER;
import static org.apache.rocketmq.spring.annotation.RocketMQMessageListener.SECRET_KEY_PLACEHOLDER;
import static org.apache.rocketmq.spring.annotation.RocketMQMessageListener.TRACE_TOPIC_PLACEHOLDER;

import lombok.Data;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/4/9 周四 13:42:00
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "carp.eventbus.rocketmq")
public class CarpEventBusProperties {

    private String group = "GID_carp_eventbus";

    private String topic = "Topic_carp_eventbus";

    private SelectorType selectorType = SelectorType.TAG;

    private String selectorExpression = "*";

    private ConsumeMode consumeMode = ConsumeMode.CONCURRENTLY;

    private MessageModel messageModel = MessageModel.CLUSTERING;

    private int consumeThreadMax = 64;

    private long consumeTimeout = 30000L;

    private String accessKey = ACCESS_KEY_PLACEHOLDER;

    private String secretKey = SECRET_KEY_PLACEHOLDER;

    private boolean enableMsgTrace = true;

    private String customizedTraceTopic = TRACE_TOPIC_PLACEHOLDER;

    private String nameServer = NAME_SERVER_PLACEHOLDER;

    private String accessChannel = ACCESS_CHANNEL_PLACEHOLDER;
}
