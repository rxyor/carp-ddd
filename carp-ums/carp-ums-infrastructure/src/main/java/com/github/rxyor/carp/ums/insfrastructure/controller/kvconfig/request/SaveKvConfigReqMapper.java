package com.github.rxyor.carp.ums.insfrastructure.controller.kvconfig.request;

import com.github.rxyor.carp.ums.application.command.kvconfig.SaveKvConfigCmd;
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
public interface SaveKvConfigReqMapper {

    SaveKvConfigReqMapper INSTANCE = Mappers.getMapper(SaveKvConfigReqMapper.class);

    SaveKvConfigCmd toSaveKvConfigCmd(SaveKvConfigReq req);
}
