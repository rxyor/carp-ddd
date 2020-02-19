package com.github.rxyor.carp.query.service.user;

import com.github.rxyor.carp.query.dto.user.UserDTO;
import com.github.rxyor.carp.query.qry.user.UserQry;
import com.github.rxyor.carp.ums.api.dto.ums.UserRetDTO;
import org.springframework.data.domain.Page;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 14:15:00
 * @since 1.0.0
 */
public interface UserQryService {

    UserRetDTO findAccount(String account);

    UserRetDTO find(String username);

    UserRetDTO findWithRoles(Long id);

    UserDTO find(Long id);

    Page<UserDTO> page(UserQry qry);
}
