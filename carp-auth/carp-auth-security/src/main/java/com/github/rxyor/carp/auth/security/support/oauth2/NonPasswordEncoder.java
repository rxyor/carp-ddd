package com.github.rxyor.carp.auth.security.support.oauth2;

import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/2/20 Wed 16:06:00
 * @since 1.0.0
 */
public class NonPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return Optional.ofNullable(charSequence).map(CharSequence::toString).orElse("");
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        if (s == null || charSequence == null) {
            return false;
        }
        return s.equals(charSequence.toString());
    }
}
