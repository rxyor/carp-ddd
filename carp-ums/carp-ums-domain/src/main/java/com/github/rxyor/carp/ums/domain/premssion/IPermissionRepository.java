package com.github.rxyor.carp.ums.domain.premssion;

import java.util.List;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 13:55:00
 * @since 1.0.0
 */
public interface IPermissionRepository {

    Permission find(Long id);

    List<Permission> findList(List<Long> idList);

    Permission save(Permission permission);

    void delete(Long id);
}
