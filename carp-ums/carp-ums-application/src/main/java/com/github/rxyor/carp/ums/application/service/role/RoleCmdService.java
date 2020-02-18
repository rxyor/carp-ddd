package com.github.rxyor.carp.ums.application.service.role;

import com.github.rxyor.carp.ums.application.command.role.DisableRoleCmd;
import com.github.rxyor.carp.ums.application.command.role.SaveRoleCmd;
import com.github.rxyor.carp.ums.application.command.role.UpdateRoleCmd;
import com.github.rxyor.carp.ums.domain.role.IRoleRepository;
import com.github.rxyor.carp.ums.domain.role.Role;
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
public class RoleCmdService {

    private final IRoleRepository roleRepository;

    /**
     * save role
     *
     * @param cmd
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Role save(SaveRoleCmd cmd) {
        Preconditions.checkArgument(cmd != null,
            "角色信息不能为空");
        HibValidatorHelper.validate(cmd);

        Role role = RoleMapper.INSTANCE.from(cmd);
        return roleRepository.save(role);
    }

    /**
     * update role
     *
     * @param cmd
     */
    @Transactional(rollbackFor = Exception.class)
    public Role update(UpdateRoleCmd cmd) {
        Preconditions.checkArgument(cmd != null,
            "角色信息不能为空");
        HibValidatorHelper.validate(cmd);

        Role dbRole = roleRepository.find(cmd.getId());
        if (dbRole == null) {
            throw new BizException(
                String.format("id[%s]角色不存在", cmd.getId()));
        }

        Role role = RoleMapper.INSTANCE.from(cmd);
        SpringBeanUtil.copyIgnoreNull(role, dbRole);
        return roleRepository.save(dbRole);
    }

    /**
     * 启用或禁用角色
     *
     * @param cmd
     */
    @Transactional(rollbackFor = Exception.class)
    public void disableOrEnable(DisableRoleCmd cmd) {
        Preconditions.checkArgument(cmd != null,
            "命令不能为空");
        HibValidatorHelper.validate(cmd);

        Role role = roleRepository.find(cmd.getId());
        Preconditions.checkArgument(role != null,
            String.format("ID:[%s]角色不存在", cmd.getId()));

        role.setDisable(cmd.getDisable());
        roleRepository.save(role);
    }

    public void delete(Long id) {
        Preconditions.checkArgument(id != null,
            "id不能为空");

        roleRepository.delete(id);
    }
}
