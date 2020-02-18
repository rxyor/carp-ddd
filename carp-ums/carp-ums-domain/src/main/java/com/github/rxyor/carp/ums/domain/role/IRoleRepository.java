package com.github.rxyor.carp.ums.domain.role;

import java.util.List;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 13:47:00
 * @since 1.0.0
 */
public interface IRoleRepository {

    Role find(Long id);

    List<Role> findList(List<Long> idList);

    Role save(Role role);

    void delete(Long id);

}
