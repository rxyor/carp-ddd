package com.github.rxyor.carp.search.infrastructure.repository.product.dao;

import com.github.rxyor.carp.search.infrastructure.repository.product.dataobj.EsProductDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/1/5 周日 22:22:00
 * @since 1.0.0
 */
public interface EsProductRepository extends ElasticsearchRepository<EsProductDO, Long> {

    Page<EsProductDO> findByNameAndNameOrSubTitleOrKeywords(String name,
        String subTitle, String keywords, Pageable pageable);
}
