package com.github.rxyor.carp.ums.application.service.clientdetails;

import com.github.rxyor.carp.ums.application.command.clientdetails.SaveClientCmd;
import com.github.rxyor.carp.ums.application.command.clientdetails.UpdateClientCmd;
import com.github.rxyor.carp.ums.domain.clientdetails.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/27 周五 23:21:00
 * @since 1.0.0
 */
@Mapper
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(
        ClientMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    Client from(SaveClientCmd cmd);

    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    Client from(UpdateClientCmd cmd);
}
