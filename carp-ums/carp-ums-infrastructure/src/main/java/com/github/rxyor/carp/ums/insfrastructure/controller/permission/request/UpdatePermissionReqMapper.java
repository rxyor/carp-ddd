package com.github.rxyor.carp.ums.insfrastructure.controller.permission.request;

import com.github.rxyor.carp.ums.application.command.permission.UpdatePermissionCmd;
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
public interface UpdatePermissionReqMapper {

    UpdatePermissionReqMapper INSTANCE = Mappers.getMapper(
        UpdatePermissionReqMapper.class);

    UpdatePermissionCmd toUpdatePermissionCmd(UpdatePermissionReq req);
}
