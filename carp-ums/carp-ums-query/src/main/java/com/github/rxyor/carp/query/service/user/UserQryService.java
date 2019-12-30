package com.github.rxyor.carp.query.service.user;

import com.github.rxyor.carp.api.dto.ums.UserRetDTO;

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
}
