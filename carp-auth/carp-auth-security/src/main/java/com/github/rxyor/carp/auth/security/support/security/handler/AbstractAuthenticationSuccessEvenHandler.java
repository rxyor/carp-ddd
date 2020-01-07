package com.github.rxyor.carp.auth.security.support.security.handler;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;

/**
 *<p>
 * oauth2 认证失败事件
 *</p>
 *
 * @author liuyang
 * @date 2020/1/7 周二 14:04:00
 * @since 1.0.0
 */
public abstract class AbstractAuthenticationSuccessEvenHandler implements
    ApplicationListener<AuthenticationSuccessEvent> {

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Authentication authentication = (Authentication) event.getSource();
        handle(authentication);
    }

    public abstract void handle(Authentication authentication);
}
