package com.github.rxyor.carp.common.rocketmq.controller;

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
@RestController
@RequestMapping("/rmq")
public class RmqTestController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @PostMapping("/send")
    public Object testSend() {
        rocketMQTemplate.convertAndSend("Topic_carp_rocketmq", "Hello World, RocketMQ!");
        return "ok";
    }
}
