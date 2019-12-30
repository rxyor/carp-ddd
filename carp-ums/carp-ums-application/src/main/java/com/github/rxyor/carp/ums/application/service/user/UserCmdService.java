package com.github.rxyor.carp.ums.application.service.user;

import com.github.rxyor.carp.ums.application.command.user.SaveUserCmd;
import com.github.rxyor.carp.ums.domain.user.IUserRepository;
import com.github.rxyor.carp.ums.domain.user.User;
import com.github.rxyor.carp.ums.shared.common.support.uitl.BizUidGenerator;
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

    private final IUserRepository IUserRepository;

    /**
     * save user
     *
     * @param cmd
     */
    @Transactional(rollbackFor = Exception.class)
    public User save(SaveUserCmd cmd) {
        Preconditions.checkArgument(cmd != null,
            "用户信息不能为空");

        User user = UserMapper.INSTANCE.from(cmd);
        user.setUsername(BizUidGenerator.nextUid());
        return IUserRepository.save(user);
    }

}
