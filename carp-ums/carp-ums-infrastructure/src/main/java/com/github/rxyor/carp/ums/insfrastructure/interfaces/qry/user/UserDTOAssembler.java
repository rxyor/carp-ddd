package com.github.rxyor.carp.ums.insfrastructure.interfaces.qry.user;

import com.github.rxyor.carp.query.dto.UserDTO;
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
public abstract class UserDTOAssembler {

    public final static UserDTOAssembler INSTANCE = Mappers.getMapper(UserDTOAssembler.class);

    public UserDTO userDTO(UserDO userDO) {
        if (userDO == null) {
            return null;
        }

        return BeanUtil.copy(userDO, UserDTO.class);
    }

    @IterableMapping(elementTargetType = UserDTO.class)
    public abstract List<UserDTO> userDTOList(List<UserDO> userDOList);

    public Page<UserDTO> userDTOPage(Page<UserDO> userDOPage) {
        if (userDOPage == null) {
            return Page.empty();
        }

        List<UserDTO> userDTOList = userDTOList(userDOPage.getContent());
        return PageUtil.cast(userDOPage, userDTOList);
    }
}
