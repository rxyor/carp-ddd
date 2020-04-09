package com.github.rxyor.carp.common.eventbus.produce;

import com.alibaba.fastjson.JSON;
import com.github.rxyor.carp.common.eventbus.core.IEvent;
import com.github.rxyor.carp.common.eventbus.listener.BusRegister;
import com.github.rxyor.carp.common.eventbus.listener.IEventListener;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/4/9 周四 16:51:00
 * @since 1.0.0
 */
@Slf4j
public class GuavaAsyncEventBus<E extends IEvent> extends BusRegister implements IEventBus<E> {

    private static final Executor EXECUTOR = new ThreadPoolExecutor(
        Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(),
        0L, TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(8192),
        new DiscardOldestPolicy() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
                log.error("GuavaAsyncEventBus executor is full, discard oldest task, Runnable:", r);
            }
        });

    private final static EventBus EVENT_BUS = new AsyncEventBus("GuavaAsyncEventBus", EXECUTOR);
    private final static GuavaAsyncEventBus<? extends IEvent> INSTANCE = new GuavaAsyncEventBus();

    public static <E extends IEvent> void send(E event) {
        INSTANCE.post(event);
    }

    public static void registerListener(IEventListener listener) {
        Objects.requireNonNull(INSTANCE, "MqEventBus instance hasn't be init");

        INSTANCE.register(listener);
    }

    public static void unregisterListener(IEventListener listener) {
        Objects.requireNonNull(INSTANCE, "MqEventBus instance hasn't be init");

        INSTANCE.unregister(listener);
    }

    @Override
    public void register(IEventListener listener) {
        super.register(listener);
    }

    @Override
    public void unregister(IEventListener listener) {
        super.unregister(listener);
    }

    @Override
    public void post(IEvent event) {
        Objects.requireNonNull(event, "event can't be null");

        if (log.isDebugEnabled()) {
            log.debug("[{}] send event, content:[{}]", this.getClass().getName(), JSON.toJSONString(event));
        }
        EVENT_BUS.post(event);
    }
}
