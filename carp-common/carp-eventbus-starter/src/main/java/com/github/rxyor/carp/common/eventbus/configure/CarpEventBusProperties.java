package com.github.rxyor.carp.common.eventbus.configure;

import lombok.Data;
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

    private String accessKey = "${rocketmq.consumer.access-key:}";

    private String secretKey = "${rocketmq.consumer.secret-key:}";

    private String customizedTraceTopic = "${rocketmq.consumer.customized-trace-topic:}";

    private String nameServer = "${rocketmq.name-server:}";

    private String accessChannel = "${rocketmq.access-channel:}";
}
