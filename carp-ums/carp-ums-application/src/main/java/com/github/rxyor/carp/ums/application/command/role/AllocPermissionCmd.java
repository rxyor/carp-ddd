package com.github.rxyor.carp.ums.application.command.role;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/19 周三 01:20:00
 * @since 1.0.0
 */
@AllArgsConstructor
@Data
public class AllocPermissionCmd {

    @NotNull(message = "角色ID不能为空")
    private Long id;

    @NotNull(message = "权限ID列表不能为空")
    private List<Long> permissionIdList;

}
