package com.github.rxyor.carp.ums.insfrastructure.repository.clientdetails;

import com.github.rxyor.carp.ums.domain.clientdetails.Client;
import com.github.rxyor.carp.ums.domain.clientdetails.IClientRepository;
import com.github.rxyor.carp.ums.insfrastructure.repository.clientdetails.dao.ClientDAO;
import com.github.rxyor.carp.ums.insfrastructure.repository.clientdetails.dataobj.ClientDO;
import com.github.rxyor.carp.ums.insfrastructure.repository.clientdetails.serializer.ClientSerializer;
import com.github.rxyor.carp.ums.shared.common.uitl.SpringBeanUtil;
import com.github.rxyor.common.core.exception.CoreException;
import com.github.rxyor.common.support.hibernate.validate.Group.Update;
import com.github.rxyor.common.support.hibernate.validate.HibValidatorHelper;
import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/19 周三 21:53:00
 * @since 1.0.0
 */
@AllArgsConstructor
@Service
public class ClientRepository implements IClientRepository {

    private final ClientDAO clientDAO;

    @Override
    public Client find(Long id) {
        Preconditions.checkArgument(id != null,
            "id不能为空");

        ClientDO clientDO = clientDAO.find(id);
        return ClientSerializer.INSTANCE.deserialize(clientDO);
    }

    @Override
    public void save(Client client) {
        Preconditions.checkArgument(client != null,
            "实体不能为空");
        HibValidatorHelper.validate(client);

        ClientDO clientDO = ClientSerializer.INSTANCE.serialize(client);
        if (clientDO != null) {
            clientDAO.save(clientDO);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Client client) {
        Preconditions.checkArgument(client != null,
            "实体不能为空");
        HibValidatorHelper.validate(client, Update.class);

        ClientDO clientDO = clientDAO.find(client.getId());
        if (clientDO == null) {
            throw new CoreException(
                String.format("id[%s]客户端不存在", client.getId()));
        }
        SpringBeanUtil.copyIgnoreNull(clientDO, client);
        this.save(client);
    }

    @Override
    public void delete(Long id) {
        Preconditions.checkArgument(id != null,
            "id不能为空");

        clientDAO.deleteById(id);
    }
}
