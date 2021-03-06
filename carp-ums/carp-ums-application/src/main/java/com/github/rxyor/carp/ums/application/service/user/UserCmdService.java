package com.github.rxyor.carp.ums.application.service.user;

import com.github.rxyor.carp.ums.application.command.user.AllocRoleCmd;
import com.github.rxyor.carp.ums.application.command.user.DisableUserCmd;
import com.github.rxyor.carp.ums.application.command.user.SaveUserCmd;
import com.github.rxyor.carp.ums.application.command.user.UpdateUserCmd;
import com.github.rxyor.carp.ums.domain.role.IRoleRepository;
import com.github.rxyor.carp.ums.domain.role.Role;
import com.github.rxyor.carp.ums.domain.user.IUserRepository;
import com.github.rxyor.carp.ums.domain.user.User;
import com.github.rxyor.carp.ums.shared.common.support.uitl.BizUidGenerator;
import com.github.rxyor.carp.ums.shared.common.uitl.SpringBeanUtil;
import com.github.rxyor.common.core.exception.BizException;
import com.github.rxyor.common.support.hibernate.validate.HibValidatorHelper;
import com.google.common.base.Preconditions;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/27 周五 23:11:00
 * @since 1.0.0
 */
@AllArgsConstructor
@Service
public class UserCmdService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;

    /**
     * save user
     *
     * @param cmd
     */
    @Transactional(rollbackFor = Exception.class)
    public User save(SaveUserCmd cmd) {
        Preconditions.checkArgument(cmd != null,
            "用户信息不能为空");
        HibValidatorHelper.validate(cmd);

        User user = UserMapper.INSTANCE.from(cmd);
        if (StringUtils.isBlank(user.getUsername())) {
            user.setUsername(BizUidGenerator.nextUid());
        }
        return userRepository.save(user);
    }

    /**
     * update user
     *
     * @param cmd
     */
    @Transactional(rollbackFor = Exception.class)
    public User update(UpdateUserCmd cmd) {
        Preconditions.checkArgument(cmd != null,
            "用户信息不能为空");
        HibValidatorHelper.validate(cmd);

        User dbUser = userRepository.find(cmd.getId());
        if (dbUser == null) {
            throw new BizException(
                String.format("id[%s]用户不存在", cmd.getId()));
        }

        User user = UserMapper.INSTANCE.from(cmd);
        SpringBeanUtil.copyIgnoreNull(user, dbUser);
        return userRepository.save(dbUser);
    }

    /**
     * 启用或禁用用户
     *
     * @param cmd
     */
    @Transactional(rollbackFor = Exception.class)
    public void disableOrEnable(DisableUserCmd cmd) {
        Preconditions.checkArgument(cmd != null,
            "命令不能为空");
        HibValidatorHelper.validate(cmd);

        User user = userRepository.find(cmd.getId());
        Preconditions.checkArgument(user != null,
            String.format("ID:[%s]用户不存在", cmd.getId()));

        user.setDisable(cmd.getDisable());
        userRepository.save(user);
    }

    public void delete(Long id) {
        Preconditions.checkArgument(id != null,
            "用户id不能为空");

        userRepository.delete(id);
    }

    public void allocRoles(AllocRoleCmd cmd) {
        Preconditions.checkArgument(cmd != null,
            "命令不能为空");
        HibValidatorHelper.validate(cmd);

        User user = userRepository.find(cmd.getId());
        Preconditions.checkArgument(user != null,
            String.format("ID:[%s]用户不存在", cmd.getId()));

        List<Role> roleList = roleRepository.findList(cmd.getRoleIdList());
        user.setRoleList(roleList);
        userRepository.save(user);
    }

}
