package com.github.rxyor.carp.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 09:39:00
 * @since 1.0.0
 */
@EnableCaching(proxyTargetClass = true)
@EnableFeignClients(basePackages = {
    "com.github.rxyor.carp.ums.api"
})
@EnableDiscoveryClient
@SpringCloudApplication
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class);
    }
}
