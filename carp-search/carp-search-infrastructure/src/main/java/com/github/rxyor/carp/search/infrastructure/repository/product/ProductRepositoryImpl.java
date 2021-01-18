package com.github.rxyor.carp.search.infrastructure.repository.product;

import com.github.rxyor.capr.commons.constants.ExceptionMsgConstant;
import com.github.rxyor.carp.search.domain.product.Product;
import com.github.rxyor.carp.search.domain.product.ProductRepository;
import com.github.rxyor.carp.search.infrastructure.repository.product.dao.EsProductDAO;
import com.github.rxyor.carp.search.infrastructure.repository.product.dataobj.EsProductDO;
import com.github.rxyor.common.core.exception.BizException;
import com.github.rxyor.common.util.lang2.BeanUtil;
import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 *<p>
 *
 *</p>
 *
 * @author qianmu.ly
 * @since 2021-01-18 11:20:22 v1.0
 */
@AllArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final EsProductDAO esProductDAO;

    @Override
    public void save(Product product) {
        Preconditions.checkArgument(product != null, ExceptionMsgConstant.NON_NULL);

        EsProductDO esProductDO = BeanUtil.copy(product, EsProductDO.class);
        esProductDAO.save(esProductDO);
    }

    @Override
    public void saveIfAbsent(Product product) {
        Preconditions.checkArgument(product != null, ExceptionMsgConstant.NON_NULL);

        boolean exists = esProductDAO.existsById(product.getId());
        if (exists) {
            throw new BizException(ExceptionMsgConstant.DATA_EXIST);
        }

        EsProductDO esProductDO = BeanUtil.copy(product, EsProductDO.class);
        esProductDAO.save(esProductDO);
    }

    @Override
    public Product findById(Long id) {
        EsProductDO esProductDO = esProductDAO.findById(id).get();
        return BeanUtil.copy(esProductDO, Product.class);
    }

    @Override
    public void deleteById(Long id) {
        esProductDAO.deleteById(id);
    }
}
