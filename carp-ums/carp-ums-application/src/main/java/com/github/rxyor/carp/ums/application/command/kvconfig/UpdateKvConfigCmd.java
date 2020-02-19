package com.github.rxyor.carp.ums.application.command.kvconfig;

import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/17 周一 23:11:00
 * @since 1.0.0
 */
@Data
public class UpdateKvConfigCmd {

    @NotNull(message = "id不能为空")
    private Long id;

    private String key;

    private String value;

    private String appId;

    private String desc;
}
