package com.github.rxyor.carp.ums.application.service.permission;

import com.github.rxyor.carp.ums.application.command.permission.DisablePermissionCmd;
import com.github.rxyor.carp.ums.application.command.permission.SavePermissionCmd;
import com.github.rxyor.carp.ums.application.command.permission.UpdatePermissionCmd;
import com.github.rxyor.carp.ums.domain.premssion.IPermissionRepository;
import com.github.rxyor.carp.ums.domain.premssion.Permission;
import com.github.rxyor.carp.ums.shared.common.uitl.SpringBeanUtil;
import com.github.rxyor.common.core.exception.BizException;
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

    private final IPermissionRepository permissionRepository;

    /**
     * save permission
     *
     * @param cmd
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Permission save(SavePermissionCmd cmd) {
        Preconditions.checkArgument(cmd != null,
            "权限信息不能为空");
        HibValidatorHelper.validate(cmd);

        Permission permission = PermissionMapper.INSTANCE.from(cmd);
        return permissionRepository.save(permission);
    }

    /**
     * update permission
     *
     * @param cmd
     */
    @Transactional(rollbackFor = Exception.class)
    public Permission update(UpdatePermissionCmd cmd) {
        Preconditions.checkArgument(cmd != null,
            "权限信息不能为空");
        HibValidatorHelper.validate(cmd);

        Permission dbPermission = permissionRepository.find(cmd.getId());
        if (dbPermission == null) {
            throw new BizException(
                String.format("id[%s]权限不存在", cmd.getId()));
        }

        Permission permission = PermissionMapper.INSTANCE.from(cmd);
        SpringBeanUtil.copyIgnoreNull(permission, dbPermission);
        return permissionRepository.save(dbPermission);
    }

    /**
     * 启用或禁用权限
     *
     * @param cmd
     */
    @Transactional(rollbackFor = Exception.class)
    public void disableOrEnable(DisablePermissionCmd cmd) {
        Preconditions.checkArgument(cmd != null,
            "命令不能为空");
        HibValidatorHelper.validate(cmd);

        Permission permission = permissionRepository.find(cmd.getId());
        Preconditions.checkArgument(permission != null,
            String.format("ID:[%s]权限不存在", cmd.getId()));

        permission.setDisable(cmd.getDisable());
        permissionRepository.save(permission);
    }

    public void delete(Long id) {
        Preconditions.checkArgument(id != null,
            "id不能为空");

        permissionRepository.delete(id);
    }
}
