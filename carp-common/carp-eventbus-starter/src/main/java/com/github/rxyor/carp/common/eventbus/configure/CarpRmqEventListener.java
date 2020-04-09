package com.github.rxyor.carp.common.eventbus.configure;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.springframework.core.annotation.AliasFor;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/4/9 周四 14:42:00
 * @since 1.0.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@RocketMQMessageListener(
    consumerGroup = "${carp.eventbus.rocketmq.group}",
    topic = "${carp.eventbus.rocketmq.topic}",
    accessKey = "${carp.eventbus.rocketmq.access-key}",
    secretKey = "${carp.eventbus.rocketmq.secret-key}",
    customizedTraceTopic = "${carp.eventbus.rocketmq.customized-trace-topic}",
    nameServer = "${carp.eventbus.rocketmq.name-server}",
    accessChannel = "${carp.eventbus.rocketmq.access-channel}"
)
public @interface CarpRmqEventListener {

    @AliasFor(annotation = RocketMQMessageListener.class, attribute = "selectorType")
    SelectorType selectorType() default SelectorType.TAG;

    @AliasFor(annotation = RocketMQMessageListener.class, attribute = "selectorExpression")
    String selectorExpression() default "*";

    @AliasFor(annotation = RocketMQMessageListener.class, attribute = "consumeMode")
    ConsumeMode consumeMode() default ConsumeMode.CONCURRENTLY;

    @AliasFor(annotation = RocketMQMessageListener.class, attribute = "messageModel")
    MessageModel messageModel() default MessageModel.CLUSTERING;

    @AliasFor(annotation = RocketMQMessageListener.class, attribute = "consumeThreadMax")
    int consumeThreadMax() default 64;

    @AliasFor(annotation = RocketMQMessageListener.class, attribute = "consumeTimeout")
    long consumeTimeout() default 30000L;

    @AliasFor(annotation = RocketMQMessageListener.class, attribute = "enableMsgTrace")
    boolean enableMsgTrace() default true;
}
