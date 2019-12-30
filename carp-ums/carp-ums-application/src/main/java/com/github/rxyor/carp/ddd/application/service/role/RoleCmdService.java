package com.github.rxyor.carp.ddd.application.service.role;

import com.github.rxyor.carp.ddd.application.command.role.SaveRoleCmd;
import com.github.rxyor.carp.ddd.domain.role.Role;
import com.github.rxyor.carp.ddd.domain.role.RoleRepository;
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

    private final RoleRepository roleRepository;

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
}
