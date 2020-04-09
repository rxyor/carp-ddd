package com.github.rxyor.carp.common.rocketmq.model;

import java.util.UUID;
import lombok.Data;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/4/9 周四 10:57:00
 * @since 1.0.0
 */
@Data
public class Event {

    private String id = UUID.randomUUID().toString();
    private String name = "Order Event";
}
