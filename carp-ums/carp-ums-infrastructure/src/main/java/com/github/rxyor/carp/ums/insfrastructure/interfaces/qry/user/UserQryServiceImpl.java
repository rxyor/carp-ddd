package com.github.rxyor.carp.ums.insfrastructure.interfaces.qry.user;

import com.github.rxyor.carp.query.dto.user.UserDTO;
import com.github.rxyor.carp.query.qry.user.UserQry;
import com.github.rxyor.carp.query.service.user.UserQryService;
import com.github.rxyor.carp.ums.api.dto.ums.UserRetDTO;
import com.github.rxyor.carp.ums.insfrastructure.repository.user.criteria.UserCriteria;
import com.github.rxyor.carp.ums.insfrastructure.repository.user.dataobj.UserDO;
import com.github.rxyor.carp.ums.shared.common.uitl.BeanUtil;
import com.google.common.base.Preconditions;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
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

    private final UserCriteria userCriteria;

    /**
     * find by username or phone or email
     *
     * @param account
     * @return
     */
    @Override
    public UserRetDTO findAccount(String account) {
        Preconditions.checkArgument(StringUtils.isNotBlank(account),
            "account can't be blank");

        List<UserDO> list = userCriteria.dao().findByUsernameOrPhoneOrEmail(
            account, account, account);
        if (list != null && list.size() > 0) {
            return BeanUtil.copy(list.get(0), UserRetDTO.class);
        }
        return null;
    }

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

        UserDO userDO = userCriteria.dao().find(username);
        if (userDO != null) {
            return BeanUtil.copy(userDO, UserRetDTO.class);
        }
        return null;
    }

    @Override
    public UserRetDTO findWithRoles(Long id) {
        Preconditions.checkArgument(id != null,
            "id不能为空");

        UserDO userDO = userCriteria.dao().find(id);
        return BeanUtil.copy(userDO, UserRetDTO.class);
    }

    /**
     * find by id
     *
     * @param id
     * @return
     */
    @Override
    public UserDTO find(Long id) {
        Preconditions.checkArgument(id != null,
            "id不能为空");

        UserDO userDO = userCriteria.dao().find(id);
        return BeanUtil.copy(userDO, UserDTO.class);
    }

    /**
     * 分页查询
     *
     * @return
     * @param qry
     */
    @Override
    public Page<UserDTO> page(UserQry qry) {
        Page<UserDO> page = userCriteria.selectPage(qry);
        return UserDTOAssembler.INSTANCE.userDTOPage(page);
    }
}
