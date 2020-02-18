package com.github.rxyor.carp.ums.insfrastructure.interfaces.qry.role;

import com.github.rxyor.carp.query.dto.role.RoleDTO;
import com.github.rxyor.carp.query.qry.role.RoleQry;
import com.github.rxyor.carp.query.service.role.RoleQryService;
import com.github.rxyor.carp.ums.insfrastructure.repository.role.dataobj.RoleDO;
import com.github.rxyor.carp.ums.insfrastructure.repository.user.criteria.RoleCriteria;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/18 周二 14:45:00
 * @since 1.0.0
 */
@AllArgsConstructor
@Service
public class RoleQryServiceImpl implements RoleQryService {

    private final RoleCriteria roleCriteria;

    @Override
    public Page<RoleDTO> page(RoleQry qry) {
        Page<RoleDO> page = roleCriteria.selectPage(qry);
        return RoleDTOAssembler.INSTANCE.roleDTOPage(page);
    }
}
