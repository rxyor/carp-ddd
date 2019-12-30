package com.github.rxyor.carp.ums.insfrastructure.interfaces.qry.user;

import com.github.rxyor.carp.ums.api.dto.ums.UserRetDTO;
import com.github.rxyor.carp.query.service.user.UserQryService;
import com.github.rxyor.carp.ums.insfrastructure.repository.user.dao.UserDAO;
import com.github.rxyor.carp.ums.insfrastructure.repository.user.dataobj.UserDO;
import com.github.rxyor.carp.ums.shared.common.uitl.BeanUtil;
import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 14:15:00
 * @since 1.0.0
 */
@AllArgsConstructor
@Service
public class UserQryServiceImpl implements UserQryService {

    private final UserDAO userDAO;

    /**
     * find by username
     *
     * @param username
     * @return
     */
    @Override
    public UserRetDTO find(String username) {
        Preconditions.checkArgument(StringUtils.isNotBlank(username),
            "username can't be blank");

        UserDO userDO = userDAO.find(username);
        if (userDO != null) {
            return BeanUtil.copy(userDO, UserRetDTO.class);
        }
        return null;
    }
}
