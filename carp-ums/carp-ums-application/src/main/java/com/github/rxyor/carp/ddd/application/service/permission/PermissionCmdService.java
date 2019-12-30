package com.github.rxyor.carp.ddd.application.service.permission;

import com.github.rxyor.carp.ddd.application.command.permission.SavePermissionCmd;
import com.github.rxyor.carp.ddd.domain.premssion.Permission;
import com.github.rxyor.carp.ddd.domain.premssion.PermissionRepository;
import com.github.rxyor.common.support.hibernate.validate.HibValidatorHelper;
import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/28 周六 17:05:00
 * @since 1.0.0
 */
@AllArgsConstructor
@Service
public class PermissionCmdService {

    private final PermissionRepository permissionRepository;

    /**
     * save Permission
     *
     * @param cmd
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Permission save(SavePermissionCmd cmd) {
        Preconditions.checkArgument(cmd != null,
            "角色信息不能为空");
        HibValidatorHelper.validate(cmd);

        Permission permission = PermissionMapper.INSTANCE.from(cmd);
        return permissionRepository.save(permission);
    }
}
