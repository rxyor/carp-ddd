package com.github.rxyor.carp.ddd.start.support.exhandler;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/11/28 周四 11:35:00
 * @since 1.0.0
 */
@AllArgsConstructor
@Data
public class ExceptionWrapper {

    private Integer code;

    private String msg;

    private Throwable e;

    public ExceptionWrapper() {
    }
}
