package com.github.rxyor.carp.common.rocketmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/4/9 周四 00:13:00
 * @since 1.0.0
 */
@EnableDiscoveryClient
@SpringBootApplication
public class RmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(RmqApplication.class);
    }
}
