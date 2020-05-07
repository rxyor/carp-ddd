package com.github.rxyor.carp.canal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CanalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CanalApplication.class);
    }
}
