package com.github.rxyor.carp.auth.security.exception;

import com.github.rxyor.common.core.enums.CoreExCodeEnum;
import com.github.rxyor.common.core.enums.KeyValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 19:25:00
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class CarpOauth2Exception extends OAuth2Exception {

    private static final long serialVersionUID = 3524097289660790685L;

    @Getter
    private Integer code;

    @Getter
    private String msg;

    public CarpOauth2Exception() {
        this(CoreExCodeEnum.FAIL.getCode(), CoreExCodeEnum.FAIL.getDesc());
    }

    public CarpOauth2Exception(String msg) {
        this(CoreExCodeEnum.FAIL.getCode(), msg);
    }

    public CarpOauth2Exception(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public CarpOauth2Exception(String msg, Throwable cause) {
        super(msg, cause);
        this.code = CoreExCodeEnum.FAIL.getCode();
        this.msg = msg;
    }

    public CarpOauth2Exception(Integer code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg;
    }

    public CarpOauth2Exception(Throwable e) {
        super(e.getMessage(), e);
        this.code = CoreExCodeEnum.FAIL.getCode();
        this.msg = e.getMessage();
    }

    public CarpOauth2Exception(KeyValue<? extends Integer> kv) {
        this(kv.getCode(), kv.getDesc());
        this.code = kv.getCode();
        this.msg = kv.getDesc();
    }

    public CarpOauth2Exception(KeyValue<? extends Integer> kv, String msg) {
        this(kv.getCode(), msg);
        this.code = kv.getCode();
        this.msg = msg;
    }

    public CarpOauth2Exception(KeyValue<? extends Integer> kv, String msg, Throwable cause) {
        this(kv.getCode(), kv.getDesc(), cause);
        this.code = kv.getCode();
        this.msg = msg;
    }

    public CarpOauth2Exception(KeyValue<? extends Integer> kv, Throwable cause) {
        this(kv.getCode(), kv.getDesc());
        this.code = kv.getCode();
        this.msg = kv.getDesc();
    }
}
