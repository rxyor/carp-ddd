package com.github.rxyor.carp.ums.insfrastructure.controller.kvconfig.request;

import com.github.rxyor.carp.ums.application.command.kvconfig.UpdateKvConfigCmd;
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
public interface UpdateKvConfigReqMapper {

    UpdateKvConfigReqMapper INSTANCE = Mappers.getMapper(UpdateKvConfigReqMapper.class);

    UpdateKvConfigCmd toUpdateKvConfigCmd(UpdateKvConfigReq req);
}
