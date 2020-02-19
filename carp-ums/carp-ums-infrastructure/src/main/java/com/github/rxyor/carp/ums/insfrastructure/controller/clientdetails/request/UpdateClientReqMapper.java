package com.github.rxyor.carp.ums.insfrastructure.controller.clientdetails.request;

import com.github.rxyor.carp.ums.application.command.clientdetails.UpdateClientCmd;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/27 周五 23:37:00
 * @since 1.0.0
 */
@Mapper
public interface UpdateClientReqMapper {

    UpdateClientReqMapper INSTANCE = Mappers.getMapper(
        UpdateClientReqMapper.class);

    UpdateClientCmd toUpdateClientCmd(UpdateClientReq req);
}
