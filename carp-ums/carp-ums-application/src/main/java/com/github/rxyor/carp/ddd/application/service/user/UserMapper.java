package com.github.rxyor.carp.ddd.application.service.user;

import com.github.rxyor.carp.ddd.domain.user.User;
import com.github.rxyor.carp.ddd.application.command.user.SaveUserCmd;
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
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "disable",ignore = true)
    @Mapping(target = "locked",ignore = true)
    @Mapping(target = "createTime",ignore = true)
    @Mapping(target = "updateTime",ignore = true)
    @Mapping(target = "roleList",ignore = true)
    User from(SaveUserCmd cmd);
}
