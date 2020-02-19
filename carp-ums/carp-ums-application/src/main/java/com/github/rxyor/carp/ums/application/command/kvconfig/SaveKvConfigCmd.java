package com.github.rxyor.carp.ums.application.command.kvconfig;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/27 周五 23:11:00
 * @since 1.0.0
 */
@Data
public class SaveKvConfigCmd {

    @NotBlank(message = "key不能为空")
    private String key;

    @NotBlank(message = "value不能为空")
    private String value;

    @NotBlank(message = "appId不能为空")
    private String appId;

    private String desc;
}
