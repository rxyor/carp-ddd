package com.github.rxyor.carp.ums.application.command.role;

import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/17 周一 12:05:00
 * @since 1.0.0
 */
@Data
public class DisableRoleCmd {

    /**
     * 用户id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 禁用标识
     */
    @NotNull(message = "禁用标识不能为空")
    @Range(min = 0L, max = 1L, message = "禁用标识无效")
    private Integer disable;

}
