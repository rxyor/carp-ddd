package com.github.rxyor.carp.auth.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.rxyor.carp.auth.security.exhandler.CarpOauth2ExceptionSerializer;
import com.github.rxyor.common.core.enums.KeyValue;
import org.springframework.http.HttpStatus;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 19:35:00
 * @since 1.0.0
 */
@JsonSerialize(using = CarpOauth2ExceptionSerializer.class)
public class UnauthorizedException extends CarpOauth2Exception {

    public UnauthorizedException() {
    }

    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException(Integer code, String msg) {
        super(code, msg);
    }

    public UnauthorizedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UnauthorizedException(Integer code, String msg, Throwable cause) {
        super(code, msg, cause);
    }

    public UnauthorizedException(Throwable e) {
        super(e);
    }

    public UnauthorizedException(KeyValue<? extends Integer> kv) {
        super(kv);
    }

    public UnauthorizedException(KeyValue<? extends Integer> kv, String msg) {
        super(kv, msg);
    }

    public UnauthorizedException(KeyValue<? extends Integer> kv, String msg,
        Throwable cause) {
        super(kv, msg, cause);
    }

    public UnauthorizedException(KeyValue<? extends Integer> kv, Throwable cause) {
        super(kv, cause);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return HttpStatus.UNAUTHORIZED.getReasonPhrase();
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }
}
