package com.github.rxyor.carp.ums.insfrastructure.repository.role;

import com.github.rxyor.carp.ums.domain.role.IRoleRepository;
import com.github.rxyor.carp.ums.domain.role.Role;
import com.github.rxyor.carp.ums.insfrastructure.repository.role.dao.RoleDAO;
import com.github.rxyor.carp.ums.insfrastructure.repository.role.dataobj.RoleDO;
import com.github.rxyor.carp.ums.shared.common.uitl.BeanUtil;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
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
public class RoleRepository implements IRoleRepository {

    private final RoleDAO roleDAO;

    @Override
    public Role find(Long id) {
        Preconditions.checkArgument(id != null,
            "id不能为空");

        RoleDO roleDO = roleDAO.find(id);
        return BeanUtil.copy(roleDO, Role.class);
    }

    @Override
    public List<Role> findList(List<Long> idList) {
        if (idList == null || idList.isEmpty()) {
            return new ArrayList<>(0);
        }

        List<RoleDO> roleDOList = roleDAO.findAllById(idList);
        return BeanUtil.copy(roleDOList, Role.class);
    }

    @Override
    public Role save(Role role) {
        Preconditions.checkArgument(role != null,
            "role can't be null");

        RoleDO roleDO = BeanUtil.copy(role, RoleDO.class);
        RoleDO ret = roleDAO.save(roleDO);
        return BeanUtil.copy(ret, Role.class);
    }

    @Override
    public void delete(Long id) {
        Preconditions.checkArgument(id != null,
            "id不能为空");

        roleDAO.deleteById(id);
    }

}
