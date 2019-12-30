package com.github.rxyor.carp.ums.start.support.exhandler;

import com.github.rxyor.common.core.enums.KeyValue;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/28 周六 11:54:00
 * @since 1.0.0
 */
@Component
@ControllerAdvice
public class GlobalExHandler extends AbstractGlobalExHandler {

    /**
     * 注意FriendlyTipEnum最好为5位以上Integer类型数字，避免与CoreExCodeEnum
     * 中枚举编码冲突
     *
     * @return
     */
    @Override
    protected Class<? extends KeyValue> getFriendlyTipEnumType() {
        return null;
    }
}
