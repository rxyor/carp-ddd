package com.github.rxyor.carp.auth.security.support.security.handler;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 *<p>
 * oauth2 认证失败事件
 *</p>
 *
 * @author liuyang
 * @date 2020/1/7 周二 14:04:00
 * @since 1.0.0
 */
public abstract class AbstractAuthenticationFailureEvenHandler implements
    ApplicationListener<AbstractAuthenticationFailureEvent> {

    @Override
    public void onApplicationEvent(AbstractAuthenticationFailureEvent event) {
        AuthenticationException authenticationException = event.getException();
        Authentication authentication = (Authentication) event.getSource();
        handle(authenticationException, authentication);
    }

    public abstract void handle(AuthenticationException authenticationException, Authentication authentication);
}
