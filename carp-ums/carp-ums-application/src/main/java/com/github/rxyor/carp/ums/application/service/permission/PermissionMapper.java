package com.github.rxyor.carp.ums.application.service.permission;

import com.github.rxyor.carp.ums.application.command.permission.SavePermissionCmd;
import com.github.rxyor.carp.ums.application.command.permission.UpdatePermissionCmd;
import com.github.rxyor.carp.ums.domain.premssion.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/28 周六 17:06:00
 * @since 1.0.0
 */
@Mapper
public interface PermissionMapper {

    PermissionMapper INSTANCE = Mappers.getMapper(
        PermissionMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "disable", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    Permission from(SavePermissionCmd cmd);

    @Mapping(target = "disable", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    Permission from(UpdatePermissionCmd cmd);
}
