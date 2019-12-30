package com.github.rxyor.carp.ums.application.service.role;

import com.github.rxyor.carp.ums.application.command.role.SaveRoleCmd;
import com.github.rxyor.carp.ums.domain.role.Role;
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
public  interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "disable",ignore = true)
    @Mapping(target = "createTime",ignore = true)
    @Mapping(target = "updateTime",ignore = true)
    @Mapping(target = "permissionList",ignore = true)
    Role from(SaveRoleCmd cmd);
}
