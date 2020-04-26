package com.github.rxyor.carp.common.service.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/4/26 周日 11:10:00
 * @since 1.0.0
 */
@EnableFeignClients(basePackages = {
    "com.github.rxyor.carp.common.service.api"
})
@EnableDiscoveryClient
@SpringBootApplication
public class CommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonApplication.class);
    }
}
