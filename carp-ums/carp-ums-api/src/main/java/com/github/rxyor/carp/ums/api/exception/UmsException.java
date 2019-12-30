package com.github.rxyor.carp.ums.api.exception;

import com.github.rxyor.common.core.enums.KeyValue;
import com.github.rxyor.common.core.exception.RpcInvokeException;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 11:41:00
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class UmsException extends RpcInvokeException {

    public UmsException() {
    }

    public UmsException(String msg) {
        super(msg);
    }

    public UmsException(Integer code, String msg) {
        super(code, msg);
    }

    public UmsException(Integer code, String msg, Throwable e) {
        super(code, msg, e);
    }

    public UmsException(Throwable e) {
        super(e);
    }

    public UmsException(String msg, Throwable e) {
        super(msg, e);
    }

    public UmsException(KeyValue<? extends Integer> kv) {
        super(kv);
    }

    public UmsException(KeyValue<? extends Integer> kv, String msg) {
        super(kv, msg);
    }

    public UmsException(KeyValue<? extends Integer> kv, String msg,
        Throwable cause) {
        super(kv, msg, cause);
    }

    public UmsException(KeyValue<? extends Integer> kv, Throwable cause) {
        super(kv, cause);
    }
}
