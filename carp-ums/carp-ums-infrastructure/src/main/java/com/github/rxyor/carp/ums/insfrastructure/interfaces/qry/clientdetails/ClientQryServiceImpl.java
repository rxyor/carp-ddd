package com.github.rxyor.carp.ums.insfrastructure.interfaces.qry.clientdetails;

import com.github.rxyor.carp.query.dto.clientdetails.ClientDTO;
import com.github.rxyor.carp.query.qry.clientdetails.ClientQry;
import com.github.rxyor.carp.query.service.clientdetails.ClientQryService;
import com.github.rxyor.carp.ums.insfrastructure.repository.clientdetails.criteria.ClientCriteria;
import com.github.rxyor.carp.ums.insfrastructure.repository.clientdetails.dataobj.ClientDO;
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
 * @date 2020/2/19 周三 22:29:00
 * @since 1.0.0
 */
@AllArgsConstructor
@Service
public class ClientQryServiceImpl implements ClientQryService {

    private final ClientCriteria clientCriteria;

    @Override
    public Page<ClientDTO> page(ClientQry qry) {
        Page<ClientDO> page = clientCriteria.selectPage(qry);
        return ClientDTOAssembler.INSTANCE.clientDTOPage(page);
    }

    @Override
    public ClientDTO find(Long id) {
        Preconditions.checkArgument(id != null,
            "id不能为空");

        ClientDO clientDO = clientCriteria.dao().find(id);
        return ClientDTOAssembler.INSTANCE.clientDTO(clientDO);
    }

}
