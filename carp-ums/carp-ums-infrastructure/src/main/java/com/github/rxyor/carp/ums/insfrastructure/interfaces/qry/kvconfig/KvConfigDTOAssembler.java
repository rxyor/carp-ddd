package com.github.rxyor.carp.ums.insfrastructure.interfaces.qry.kvconfig;

import com.github.rxyor.carp.query.dto.kvconfig.KvConfigDTO;
import com.github.rxyor.carp.ums.insfrastructure.repository.KvConfigDO;
import com.github.rxyor.carp.ums.shared.common.uitl.PageUtil;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/17 周一 00:03:00
 * @since 1.0.0
 */
@Mapper
public abstract class KvConfigDTOAssembler {

    public final static KvConfigDTOAssembler INSTANCE = Mappers.getMapper(
        KvConfigDTOAssembler.class);

    public abstract KvConfigDTO kvConfigDTO(KvConfigDO kvConfigDO);

    @IterableMapping(elementTargetType = KvConfigDTO.class)
    public abstract List<KvConfigDTO> kvConfigDTOList(List<KvConfigDO> kvConfigDOList);

    public Page<KvConfigDTO> kvConfigDTOPage(Page<KvConfigDO> kvConfigDOPage) {
        if (kvConfigDOPage == null) {
            return Page.empty();
        }

        List<KvConfigDTO> kvConfigDTOList = kvConfigDTOList(kvConfigDOPage.getContent());
        return PageUtil.cast(kvConfigDOPage, kvConfigDTOList);
    }
}
