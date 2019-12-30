package com.github.rxyor.carp.ums.start.support.exhandler;

import com.github.rxyor.common.core.enums.CoreExCodeEnum;
import com.github.rxyor.common.core.model.R;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/11/30 周六 00:07:00
 * @since 1.0.0
 */
@Slf4j
public abstract class AbstractGlobalExHandler extends AbstractExHandler<R> {

    @Override
    protected R convert2R(ExceptionWrapper wrapper) {
        Integer code = Optional.ofNullable(wrapper).map(ExceptionWrapper::getCode)
            .orElse(CoreExCodeEnum.FAIL.getCode());
        String msg = Optional.ofNullable(wrapper).map(ExceptionWrapper::getMsg)
            .orElse(CoreExCodeEnum.FAIL.getDesc());
        Throwable e = wrapper.getE();
        log.error("遇到: {}, 异常错误信息:\n", e.getClass().getName(), e);
        return R.fail(code, msg, e.getMessage());
    }
}
