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
public class MethodNotAllowedException extends CarpOauth2Exception {

    public MethodNotAllowedException() {
    }

    public MethodNotAllowedException(String msg) {
        super(msg);
    }

    public MethodNotAllowedException(Integer code, String msg) {
        super(code, msg);
    }

    public MethodNotAllowedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public MethodNotAllowedException(Integer code, String msg, Throwable cause) {
        super(code, msg, cause);
    }

    public MethodNotAllowedException(Throwable e) {
        super(e);
    }

    public MethodNotAllowedException(KeyValue<? extends Integer> kv) {
        super(kv);
    }

    public MethodNotAllowedException(KeyValue<? extends Integer> kv, String msg) {
        super(kv, msg);
    }

    public MethodNotAllowedException(KeyValue<? extends Integer> kv, String msg,
        Throwable cause) {
        super(kv, msg, cause);
    }

    public MethodNotAllowedException(KeyValue<? extends Integer> kv, Throwable cause) {
        super(kv, cause);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase();
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.METHOD_NOT_ALLOWED.value();
    }
}
