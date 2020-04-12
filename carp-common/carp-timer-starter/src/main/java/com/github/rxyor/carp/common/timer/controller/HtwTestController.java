package com.github.rxyor.carp.common.timer.controller;

import io.netty.util.TimerTask;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
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
 * @date 2020/4/11 周六 21:18:00
 * @since 1.0.0
 */
@Api(value = "hwt")
@RestController
@RequestMapping("/hwt")
public class HtwTestController {

    @Autowired
    private RedissonClient redissonClient;

    @ApiOperation(tags = "hwt", value = "redis test")
    @PostMapping("/set")
    public Object testSet() {
        TimerTask task= timeout -> System.out.println(Thread.currentThread().getName());
        RBucket<TimerTask> bucket = redissonClient.getBucket("hwt_test");
        bucket.set(task);

        return "ok";
    }
}
