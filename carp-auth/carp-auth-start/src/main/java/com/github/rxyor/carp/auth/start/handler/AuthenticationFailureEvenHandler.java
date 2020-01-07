package com.github.rxyor.carp.auth.start.handler;

import com.github.rxyor.carp.auth.security.support.security.handler.AbstractAuthenticationFailureEvenHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/1/7 周二 14:02:00
 * @since 1.0.0
 */
@Slf4j
@Component
public class AuthenticationFailureEvenHandler extends AbstractAuthenticationFailureEvenHandler {

    @Override
    public void handle(AuthenticationException authenticationException, Authentication authentication) {
        log.info("登录失败:用户{},错误:{}", authentication.getPrincipal(), authenticationException.getLocalizedMessage());
    }
}
