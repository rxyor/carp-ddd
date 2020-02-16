package com.github.rxyor.carp.query.service.user;

import com.github.rxyor.carp.query.qry.UserQry;
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

    /**
     * find by username
     *
     * @param username
     * @return
     */
    UserRetDTO find(String username);

    /**
     * 分页查询
     *
     * @return
     */
    Page<UserRetDTO> page(UserQry qry);
}
