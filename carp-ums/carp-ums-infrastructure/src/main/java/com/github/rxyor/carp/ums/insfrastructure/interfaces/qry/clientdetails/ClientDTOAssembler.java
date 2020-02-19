package com.github.rxyor.carp.ums.insfrastructure.interfaces.qry.clientdetails;

import com.github.rxyor.carp.query.dto.clientdetails.ClientDTO;
import com.github.rxyor.carp.ums.insfrastructure.repository.clientdetails.dataobj.ClientDO;
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
public abstract class ClientDTOAssembler {

    public final static ClientDTOAssembler INSTANCE = Mappers.getMapper(
        ClientDTOAssembler.class);

    public abstract ClientDTO clientDTO(ClientDO clientDO);

    @IterableMapping(elementTargetType = ClientDTO.class)
    public abstract List<ClientDTO> clientDTOList(List<ClientDO> clientDOList);

    public Page<ClientDTO> clientDTOPage(Page<ClientDO> clientDOPage) {
        if (clientDOPage == null) {
            return Page.empty();
        }

        List<ClientDTO> clientDTOList = clientDTOList(clientDOPage.getContent());
        return PageUtil.cast(clientDOPage, clientDTOList);
    }
}
