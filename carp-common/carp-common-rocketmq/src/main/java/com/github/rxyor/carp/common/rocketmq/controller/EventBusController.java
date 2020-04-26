package com.github.rxyor.carp.common.rocketmq.controller;

import com.github.rxyor.carp.common.rocketmq.model.MqEvent;
import com.github.rxyor.carp.spring.boot.eventbus.produce.MqEventBus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/4/9 周四 00:26:00
 * @since 1.0.0
 */
@Api(value = "rmq")
@RestController
@RequestMapping("/rmq/eventbus")
public class EventBusController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @ApiOperation(tags = "eventbus", value = "发送MQ异步事件")
    @PostMapping("/mq/event/send")
    public Object testSendEvent() {
        MqEvent event = new MqEvent();
        event.setOrderNo("9999999966661111");
        event.setCreateTime(new Date());
        event.setPrice(new BigDecimal(999));
        event.setCount(10);
        MqEventBus.send(event, 10L);
        return "ok";
    }
}