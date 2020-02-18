package com.github.rxyor.carp.ums.insfrastructure.interfaces.qry.role;

import com.github.rxyor.carp.query.dto.role.RoleDTO;
import com.github.rxyor.carp.ums.insfrastructure.repository.role.dataobj.RoleDO;
import com.github.rxyor.carp.ums.shared.common.uitl.BeanUtil;
import com.github.rxyor.carp.ums.shared.common.uitl.PageUtil;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/17 周一 00:03:00
 * @since 1.0.0
 */
@Mapper
public abstract class RoleDTOAssembler {

    public final static RoleDTOAssembler INSTANCE = Mappers.getMapper(RoleDTOAssembler.class);

    public RoleDTO roleDTO(RoleDO roleDO) {
        if (roleDO == null) {
            return null;
        }

        return BeanUtil.copy(roleDO, RoleDTO.class);
    }

    @IterableMapping(elementTargetType = RoleDTO.class)
    public abstract List<RoleDTO> roleDTOList(List<RoleDO> roleDOList);

    public Page<RoleDTO> roleDTOPage(Page<RoleDO> roleDOPage) {
        if (roleDOPage == null) {
            return Page.empty();
        }

        List<RoleDTO> roleDTOList = roleDTOList(roleDOPage.getContent());
        return PageUtil.cast(roleDOPage, roleDTOList);
    }
}
