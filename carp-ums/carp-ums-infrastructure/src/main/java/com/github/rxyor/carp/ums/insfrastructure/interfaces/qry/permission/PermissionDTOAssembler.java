package com.github.rxyor.carp.ums.insfrastructure.interfaces.qry.permission;

import com.github.rxyor.carp.query.dto.permission.PermissionDTO;
import com.github.rxyor.carp.ums.insfrastructure.repository.premssion.dataobj.PermissionDO;
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
public abstract class PermissionDTOAssembler {

    public final static PermissionDTOAssembler INSTANCE = Mappers.getMapper(
        PermissionDTOAssembler.class);

    public PermissionDTO permissionDTO(PermissionDO permissionDO) {
        if (permissionDO == null) {
            return null;
        }

        return BeanUtil.copy(permissionDO, PermissionDTO.class);
    }

    @IterableMapping(elementTargetType = PermissionDTO.class)
    public abstract List<PermissionDTO> permissionDTOList(List<PermissionDO> permissionDOList);

    public Page<PermissionDTO> permissionDTOPage(Page<PermissionDO> permissionDOPage) {
        if (permissionDOPage == null) {
            return Page.empty();
        }

        List<PermissionDTO> permissionDTOList = permissionDTOList(permissionDOPage.getContent());
        return PageUtil.cast(permissionDOPage, permissionDTOList);
    }
}
