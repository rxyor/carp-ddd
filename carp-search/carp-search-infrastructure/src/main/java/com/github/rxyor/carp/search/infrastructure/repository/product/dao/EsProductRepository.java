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

    /**
     *<p>
     * 查找
     *</p>
     *
     * @author qianmu.ly
     * @since 2021-01-13 16:32:31 v1.0
     * @param productNo productNo
     * @param productName productName
     * @param productTitle productTitle
     * @param pageable pageable
     * @return []
     */
    Page<EsProductDO> findByProductNoOrProductNameOrProductTitle(
        String productNo,
        String productName,
        String productTitle, Pageable pageable);
}
