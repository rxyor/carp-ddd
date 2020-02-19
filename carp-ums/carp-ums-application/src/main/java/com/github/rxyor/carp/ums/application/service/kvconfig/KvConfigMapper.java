package com.github.rxyor.carp.ums.application.service.kvconfig;

import com.github.rxyor.carp.ums.application.command.kvconfig.SaveKvConfigCmd;
import com.github.rxyor.carp.ums.application.command.kvconfig.UpdateKvConfigCmd;
import com.github.rxyor.carp.ums.domain.kvconfig.KvConfig;
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
public interface KvConfigMapper {

    KvConfigMapper INSTANCE = Mappers.getMapper(
        KvConfigMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    KvConfig from(SaveKvConfigCmd cmd);

    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    KvConfig from(UpdateKvConfigCmd cmd);
}
