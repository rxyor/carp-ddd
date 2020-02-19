package com.github.rxyor.carp.ums.insfrastructure.repository.kvconfig.serializer;

import com.github.rxyor.carp.ums.domain.kvconfig.KvConfig;
import com.github.rxyor.carp.ums.insfrastructure.repository.KvConfigDO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/19 周三 15:10:00
 * @since 1.0.0
 */
@Mapper
public interface KvConfigSerializer {

    KvConfigSerializer INSTANCE = Mappers.getMapper(KvConfigSerializer.class);

    KvConfig deserialize(KvConfigDO source);

    List<KvConfig> deserialize(List<KvConfigDO> source);

    KvConfigDO serialize(KvConfig source);
}
