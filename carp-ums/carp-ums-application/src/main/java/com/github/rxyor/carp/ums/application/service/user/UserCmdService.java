package com.github.rxyor.carp.ums.application.service.user;

import com.github.rxyor.carp.ums.application.command.user.DisableUserCmd;
import com.github.rxyor.carp.ums.application.command.user.SaveUserCmd;
import com.github.rxyor.carp.ums.domain.user.IUserRepository;
import com.github.rxyor.carp.ums.domain.user.User;
import com.github.rxyor.carp.ums.shared.common.support.uitl.BizUidGenerator;
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
 * @date 2019/12/27 周五 23:11:00
 * @since 1.0.0
 */
@AllArgsConstructor
@Service
public class UserCmdService {

    private final IUserRepository userRepository;

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
        user.setUsername(BizUidGenerator.nextUid());
        return userRepository.save(user);
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

    public void delete(Long id){
        Preconditions.checkArgument(id!=null,
            "用户id不能为空");

        userRepository.delete(id);
    }

}
