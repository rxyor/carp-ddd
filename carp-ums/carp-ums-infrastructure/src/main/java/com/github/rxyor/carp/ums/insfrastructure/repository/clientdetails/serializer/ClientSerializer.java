package com.github.rxyor.carp.ums.insfrastructure.repository.clientdetails.serializer;

import com.github.rxyor.carp.ums.domain.clientdetails.Client;
import com.github.rxyor.carp.ums.insfrastructure.repository.clientdetails.dataobj.ClientDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/19 周三 22:03:00
 * @since 1.0.0
 */
@Mapper
public interface ClientSerializer {

    ClientSerializer INSTANCE = Mappers.getMapper(ClientSerializer.class);

    Client deserialize(ClientDO source);

    List<Client> deserialize(List<ClientDO> source);

    ClientDO serialize(Client source);

}
