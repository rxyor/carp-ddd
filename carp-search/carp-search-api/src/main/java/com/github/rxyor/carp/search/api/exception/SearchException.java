package com.github.rxyor.carp.search.api.exception;

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
public class SearchException extends RpcInvokeException {

    public SearchException() {
    }

    public SearchException(String msg) {
        super(msg);
    }

    public SearchException(Integer code, String msg) {
        super(code, msg);
    }

    public SearchException(Integer code, String msg, Throwable e) {
        super(code, msg, e);
    }

    public SearchException(Throwable e) {
        super(e);
    }

    public SearchException(String msg, Throwable e) {
        super(msg, e);
    }

    public SearchException(KeyValue<? extends Integer> kv) {
        super(kv);
    }

    public SearchException(KeyValue<? extends Integer> kv, String msg) {
        super(kv, msg);
    }

    public SearchException(KeyValue<? extends Integer> kv, String msg,
        Throwable cause) {
        super(kv, msg, cause);
    }

    public SearchException(KeyValue<? extends Integer> kv, Throwable cause) {
        super(kv, cause);
    }
}
