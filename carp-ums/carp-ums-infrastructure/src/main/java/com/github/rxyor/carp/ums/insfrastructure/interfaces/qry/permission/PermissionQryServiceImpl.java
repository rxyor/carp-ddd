package com.github.rxyor.carp.ums.insfrastructure.interfaces.qry.permission;

import com.github.rxyor.carp.query.dto.permission.PermissionDTO;
import com.github.rxyor.carp.query.qry.permission.PermissionQry;
import com.github.rxyor.carp.query.service.permission.PermissionQryService;
import com.github.rxyor.carp.ums.insfrastructure.repository.premssion.criteria.PermissionCriteria;
import com.github.rxyor.carp.ums.insfrastructure.repository.premssion.dataobj.PermissionDO;
import com.github.rxyor.carp.ums.shared.common.uitl.BeanUtil;
import com.google.common.base.Preconditions;
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
public class PermissionQryServiceImpl implements PermissionQryService {

    private final PermissionCriteria permissionCriteria;

    @Override
    public Page<PermissionDTO> page(PermissionQry qry) {
        Page<PermissionDO> page = permissionCriteria.selectPage(qry);
        return PermissionDTOAssembler.INSTANCE.permissionDTOPage(page);
    }

    @Override
    public PermissionDTO find(Long id) {
        Preconditions.checkArgument(id != null,
            "id不能为空");

        PermissionDO permissionDO = permissionCriteria.dao().find(id);
        return BeanUtil.copy(permissionDO, PermissionDTO.class);
    }
}
