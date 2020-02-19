package com.github.rxyor.carp.query.service.kvconfig;

import com.github.rxyor.carp.query.dto.kvconfig.KvConfigDTO;
import com.github.rxyor.carp.query.qry.kvconfig.KvConfigQry;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/2/18 周二 14:44:00
 * @since 1.0.0
 */
public interface KvConfigQryService {

    Page<KvConfigDTO> page(KvConfigQry qry);

    KvConfigDTO find(Long id);

    List<KvConfigDTO> find(String key, String appId);

}
