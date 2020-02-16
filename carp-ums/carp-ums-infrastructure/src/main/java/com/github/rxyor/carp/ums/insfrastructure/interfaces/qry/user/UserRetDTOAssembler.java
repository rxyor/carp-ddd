package com.github.rxyor.carp.ums.insfrastructure.interfaces.qry.user;

import com.github.rxyor.carp.ums.api.dto.ums.UserRetDTO;
import com.github.rxyor.carp.ums.insfrastructure.repository.user.dataobj.UserDO;
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
public abstract class UserRetDTOAssembler {

    public final static UserRetDTOAssembler INSTANCE = Mappers.getMapper(UserRetDTOAssembler.class);

    public UserRetDTO userRetDTO(UserDO userDO) {
        if (userDO == null) {
            return null;
        }

        return BeanUtil.copy(userDO, UserRetDTO.class);
    }

    @IterableMapping(elementTargetType = UserRetDTO.class)
    public abstract List<UserRetDTO> userRetDTOList(List<UserDO> userDOList);

    public Page<UserRetDTO> userRetDTOPage(Page<UserDO> userDOPage) {
        if (userDOPage == null) {
            return Page.empty();
        }

        List<UserRetDTO> userRetDTOList = userRetDTOList(userDOPage.getContent());
        return PageUtil.cast(userDOPage, userRetDTOList);
    }
}
