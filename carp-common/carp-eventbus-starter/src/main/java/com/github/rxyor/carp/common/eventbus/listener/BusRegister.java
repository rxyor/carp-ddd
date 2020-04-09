package com.github.rxyor.carp.common.eventbus.listener;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/4/9 周四 16:01:00
 * @since 1.0.0
 */
public class BusRegister {

    private final static Map<String, IEventListener> LISTENERS = new ConcurrentHashMap<>();

    public void register(IEventListener listener) {
        Objects.requireNonNull(listener, "listener can't be null, register fail!");
        LISTENERS.putIfAbsent(listener.getClass().getName(), listener);
    }

    public void unregister(IEventListener listener) {
        Objects.requireNonNull(listener, "listener can't be null, unregister fail!");
        LISTENERS.remove(listener.getClass().getName());
    }
}
