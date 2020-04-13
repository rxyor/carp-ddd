package com.github.rxyor.carp.common.rocketmq.model;

import com.github.rxyor.carp.spring.boot.eventbus.core.IEvent;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/4/9 周四 17:24:00
 * @since 1.0.0
 */
@Data
public class MqEvent implements IEvent {

    private static final long serialVersionUID = 8905941578121623522L;

    private String orderNo;
    private BigDecimal price;
    private Date createTime;
    private Integer count;

    @Override
    public String getEventKey() {
        return IEvent.uuid();
    }
}
