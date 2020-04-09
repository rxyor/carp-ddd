package com.github.rxyor.carp.common.rocketmq.controller;

import com.alibaba.fastjson.JSON;
import com.github.rxyor.carp.common.rocketmq.model.Event;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/rmq")
public class RmqTestController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @ApiOperation(tags = "rmq", value = "send test")
    @PostMapping("/send")
    public Object testSend() {
        String s = "Hello World, RocketMQ!";
        rocketMQTemplate.convertAndSend("Topic_carp_rocketmq", JSON.toJSONString(new Event()));
        return "ok";
    }
}
