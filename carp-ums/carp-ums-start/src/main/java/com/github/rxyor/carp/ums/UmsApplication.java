package com.github.rxyor.carp.ums;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *<p>
 * 通过SecurityContextPersistenceFilter过滤器取用户信息时会进行一次调用，所以要打开EnableFeignClients
 *</p>
 *
 * @author liuyang
 * @date 2019/12/27 周五 11:01:00
 * @since 1.0.0
 */
@EnableFeignClients(basePackages = {
    "com.github.rxyor.carp.ums.api"
})
@EnableDiscoveryClient
@SpringBootApplication
public class UmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UmsApplication.class);
    }
}
