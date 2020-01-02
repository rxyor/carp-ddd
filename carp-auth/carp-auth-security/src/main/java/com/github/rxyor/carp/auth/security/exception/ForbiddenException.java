package com.github.rxyor.carp.auth.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
public class ForbiddenException extends CarpOauth2Exception {

    public ForbiddenException() {
    }

    public ForbiddenException(String msg) {
        super(msg);
    }

    public ForbiddenException(Integer code, String msg) {
        super(code, msg);
    }

    public ForbiddenException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ForbiddenException(Integer code, String msg, Throwable cause) {
        super(code, msg, cause);
    }

    public ForbiddenException(Throwable e) {
        super(e);
    }

    public ForbiddenException(KeyValue<? extends Integer> kv) {
        super(kv);
    }

    public ForbiddenException(KeyValue<? extends Integer> kv, String msg) {
        super(kv, msg);
    }

    public ForbiddenException(KeyValue<? extends Integer> kv, String msg,
        Throwable cause) {
        super(kv, msg, cause);
    }

    public ForbiddenException(KeyValue<? extends Integer> kv, Throwable cause) {
        super(kv, cause);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return HttpStatus.FORBIDDEN.getReasonPhrase();
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.FORBIDDEN.value();
    }
}
