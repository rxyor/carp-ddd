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
public class ServerErrorException extends CarpOauth2Exception {

    public ServerErrorException() {
    }

    public ServerErrorException(String msg) {
        super(msg);
    }

    public ServerErrorException(Integer code, String msg) {
        super(code, msg);
    }

    public ServerErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ServerErrorException(Integer code, String msg, Throwable cause) {
        super(code, msg, cause);
    }

    public ServerErrorException(Throwable e) {
        super(e);
    }

    public ServerErrorException(KeyValue<? extends Integer> kv) {
        super(kv);
    }

    public ServerErrorException(KeyValue<? extends Integer> kv, String msg) {
        super(kv, msg);
    }

    public ServerErrorException(KeyValue<? extends Integer> kv, String msg, Throwable cause) {
        super(kv, msg, cause);
    }

    public ServerErrorException(KeyValue<? extends Integer> kv, Throwable cause) {
        super(kv, cause);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
