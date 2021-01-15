package com.github.rxyor.carp.search.application.service;

import com.github.rxyor.carp.search.application.cmd.user.UserCmd;
import com.github.rxyor.carp.search.domain.user.User;
import com.github.rxyor.carp.search.domain.user.UserRepository;
import com.github.rxyor.common.support.hibernate.validate.HibValidatorHelper;
import com.github.rxyor.common.util.lang2.BeanUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *<p>
 *
 *</p>
 *
 * @author qianmu.ly
 * @since 2021-01-15 13:49:00 v1.0
 */
@AllArgsConstructor
@Service
public class UserCmdService {

    private final UserRepository userRepository;

    /**
     *<p>
     *  保存用户
     *</p>
     *
     * @author qianmu.ly
     * @since 2021-01-15 13:49:28 v1.0
     * @param cmd cmd
     */
    public void saveUser(UserCmd cmd) {
        HibValidatorHelper.validate(cmd);

        User user = BeanUtil.copy(cmd, User.class);
        userRepository.save(user);
    }
}
