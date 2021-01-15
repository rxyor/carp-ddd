package com.github.rxyor.carp.search.infrastructure.repository.user;

import com.github.rxyor.carp.search.domain.user.User;
import com.github.rxyor.carp.search.domain.user.UserRepository;
import com.github.rxyor.carp.search.infrastructure.repository.user.dao.EsUserDAO;
import com.github.rxyor.carp.search.infrastructure.repository.user.dataobj.EsUserDO;
import com.github.rxyor.common.util.lang2.BeanUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 *<p>
 *
 *</p>
 *
 * @author qianmu.ly
 * @since 2021-01-15 13:30:38 v1.0
 */
@AllArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final EsUserDAO esUserDAO;

    @Override
    public void save(User user) {
        EsUserDO esUserDO = BeanUtil.copy(user, EsUserDO.class);
        esUserDAO.save(esUserDO);
    }
}
