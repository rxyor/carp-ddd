package com.github.rxyor.carp.ums.insfrastructure.interfaces.qry.kvconfig;

import com.github.rxyor.carp.query.dto.kvconfig.KvConfigDTO;
import com.github.rxyor.carp.query.qry.kvconfig.KvConfigQry;
import com.github.rxyor.carp.query.service.kvconfig.KvConfigQryService;
import com.github.rxyor.carp.ums.insfrastructure.repository.kvconfig.criteria.KvConfigCriteria;
import com.github.rxyor.carp.ums.insfrastructure.repository.kvconfig.dataobj.KvConfigDO;
import com.github.rxyor.carp.ums.shared.common.uitl.BeanUtil;
import com.google.common.base.Preconditions;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/18 周二 14:45:00
 * @since 1.0.0
 */
@AllArgsConstructor
@Service
public class KvConfigQryServiceImpl implements KvConfigQryService {

    private final KvConfigCriteria kvConfigCriteria;

    @Override
    public Page<KvConfigDTO> page(KvConfigQry qry) {
        Page<KvConfigDO> page = kvConfigCriteria.selectPage(qry);
        return KvConfigDTOAssembler.INSTANCE.kvConfigDTOPage(page);
    }

    @Override
    public KvConfigDTO find(Long id) {
        Preconditions.checkArgument(id != null,
            "id不能为空");

        KvConfigDO kvConfigDO = kvConfigCriteria.dao().find(id);
        return BeanUtil.copy(kvConfigDO, KvConfigDTO.class);
    }

    @Override
    public List<KvConfigDTO> find(String key, String appId) {
        Preconditions.checkArgument(StringUtils.isNotBlank(key),
            "key不能为空");
        Preconditions.checkArgument(StringUtils.isNotBlank(appId),
            "appId不能为空");

        List<KvConfigDO> list = kvConfigCriteria.dao().findAllByKeyAndAppId(key, appId);
        return BeanUtil.copy(list, KvConfigDTO.class);
    }

}
