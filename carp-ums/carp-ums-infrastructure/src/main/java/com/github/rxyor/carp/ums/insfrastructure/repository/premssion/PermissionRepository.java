package com.github.rxyor.carp.ums.insfrastructure.repository.premssion;

import com.github.rxyor.carp.ums.domain.premssion.IPermissionRepository;
import com.github.rxyor.carp.ums.domain.premssion.Permission;
import com.github.rxyor.carp.ums.insfrastructure.repository.premssion.dao.PermissionDAO;
import com.github.rxyor.carp.ums.insfrastructure.repository.premssion.dataobj.PermissionDO;
import com.github.rxyor.carp.ums.shared.common.uitl.BeanUtil;
import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/30 周一 13:51:00
 * @since 1.0.0
 */
@AllArgsConstructor
@Repository
public class PermissionRepository implements IPermissionRepository {

    private final PermissionDAO permissionDAO;

    @Override
    public Permission save(Permission permission) {
        Preconditions.checkArgument(permission != null,
            "permission can't be null");

        PermissionDO permissionDO = BeanUtil.copy(permission, PermissionDO.class);
        PermissionDO ret = permissionDAO.save(permissionDO);
        return BeanUtil.copy(ret, Permission.class);
    }

    @Override
    public Permission find(Long id) {
        Preconditions.checkArgument(id != null,
            "id不能为空");

        PermissionDO permissionDO = permissionDAO.find(id);
        return BeanUtil.copy(permissionDO, Permission.class);
    }

    @Override
    public void delete(Long id) {
        Preconditions.checkArgument(id != null,
            "id不能为空");

        permissionDAO.deleteById(id);
    }
}
