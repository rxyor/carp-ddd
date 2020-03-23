package com.github.rxyor.carp.ums.insfrastructure.repository.user;

import com.github.rxyor.carp.ums.api.enums.common.YesNoEnum;
import com.github.rxyor.carp.ums.domain.user.IUserRepository;
import com.github.rxyor.carp.ums.domain.user.User;
import com.github.rxyor.carp.ums.insfrastructure.repository.user.dao.UserDAO;
import com.github.rxyor.carp.ums.insfrastructure.repository.user.dataobj.UserDO;
import com.github.rxyor.carp.ums.shared.common.uitl.BeanUtil;
import com.github.rxyor.common.core.exception.BizException;
import com.github.rxyor.common.core.exception.MysqlException;
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
    public User find(Long id) {
        Preconditions.checkArgument(id != null,
            "id不能为空");

        UserDO userDO = userDAO.find(id);
        return BeanUtil.copy(userDO, User.class);
    }

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

    @Override
    public void delete(Long id) {
        Preconditions.checkArgument(id != null,
            "id不能为空");
        UserDO exit = userDAO.find(id);
        if (exit == null) {
            throw new MysqlException(String.format("id:[%s]用户不存在", id.toString()));
        } else if (YesNoEnum.Y.getCode().equals(exit.getSys())) {
            throw new BizException("系统用户不能删除");
        }
        userDAO.deleteById(id);
    }
}
