package com.github.rxyor.carp.search.infrastructure.repository.user.dao;

import com.github.rxyor.carp.search.infrastructure.repository.user.dataobj.EsUserDO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *<p>
 *
 *</p>
 *
 * @author qianmu.ly
 * @since 2021-01-15 12:59:09 v1.0
 */
public interface EsUserDAO extends ElasticsearchRepository<EsUserDO, Long> {

}
