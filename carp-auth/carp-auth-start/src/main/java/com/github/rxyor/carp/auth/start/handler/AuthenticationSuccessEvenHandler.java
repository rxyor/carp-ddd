package com.github.rxyor.carp.auth.start.handler;

import com.github.rxyor.carp.auth.security.support.security.handler.AbstractAuthenticationSuccessEvenHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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
public class AuthenticationSuccessEvenHandler extends AbstractAuthenticationSuccessEvenHandler {

    @Override
    public void handle(Authentication authentication) {
        log.info("登录成功:用户{}", authentication.getPrincipal());
    }
}
