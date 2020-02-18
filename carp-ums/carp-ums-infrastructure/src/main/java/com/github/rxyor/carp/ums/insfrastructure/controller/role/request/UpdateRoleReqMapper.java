package com.github.rxyor.carp.ums.insfrastructure.controller.role.request;

import com.github.rxyor.carp.ums.application.command.role.UpdateRoleCmd;
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
public interface UpdateRoleReqMapper {

    UpdateRoleReqMapper INSTANCE = Mappers.getMapper(UpdateRoleReqMapper.class);

    UpdateRoleCmd toUpdateRoleCmd(UpdateRoleReq req);
}
