package com.github.rxyor.carp.ums.insfrastructure.repository.user;

import com.github.rxyor.carp.ums.domain.user.IUserRepository;
import com.github.rxyor.carp.ums.domain.user.User;
import com.github.rxyor.carp.ums.insfrastructure.repository.user.dao.UserDAO;
import com.github.rxyor.carp.ums.insfrastructure.repository.user.dataobj.UserDO;
import com.github.rxyor.carp.ums.shared.common.uitl.BeanUtil;
import com.google.common.base.Preconditions;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 13:41:00
 * @since 1.0.0
 */
@AllArgsConstructor
@Repository
public class UserRepository implements IUserRepository {

    private final UserDAO userDAO;

    @Override
    public User find(String username) {
        UserDO userDO = userDAO.find(username);
        return BeanUtil.copy(userDO, User.class);
    }

    @Override
    public List<User> findAll() {
        List<UserDO> list = userDAO.findAll();
        return BeanUtil.copy(list, User.class);
    }

    @Override
    public User save(User user) {
        Preconditions.checkArgument(user != null,
            "user can't be null");

        UserDO userDO = BeanUtil.copy(user, UserDO.class);
        UserDO ret = userDAO.save(userDO);
        return BeanUtil.copy(ret, User.class);
    }
}
