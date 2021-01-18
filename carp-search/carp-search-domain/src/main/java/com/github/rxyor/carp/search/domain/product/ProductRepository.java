package com.github.rxyor.carp.search.domain.product;

/**
 *<p>
 * 产品仓储层
 *</p>
 *
 * @author qianmu.ly
 * @since 2021-01-18 11:18:47 v1.0
 */
@SuppressWarnings("all")
public interface ProductRepository {

    void save(Product product);

    void saveIfAbsent(Product product);

    Product findById(Long id);

    void deleteById(Long id);
}
