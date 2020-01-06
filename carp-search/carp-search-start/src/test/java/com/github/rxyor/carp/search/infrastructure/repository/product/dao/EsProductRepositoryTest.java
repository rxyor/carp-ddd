package com.github.rxyor.carp.search.infrastructure.repository.product.dao;

import com.github.rxyor.carp.search.SpringWithJUnit5IT;
import com.github.rxyor.carp.search.infrastructure.repository.product.dataobj.EsProductDO;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/1/5 周日 22:41:00
 * @since 1.0.0
 */
public class EsProductRepositoryTest extends SpringWithJUnit5IT {

    @Autowired
    EsProductRepository esProductRepository;

    @Test
    void findByNaAndNameOrSubTitleOrKeywords() {
        Iterator<EsProductDO> iter = esProductRepository.findAll().iterator();
        List<EsProductDO> list = new ArrayList<>(16);
        while (iter.hasNext()){
            list.add(iter.next());
        }
        log.info("result:{}",list);
    }

    @Test
    void save( ){
        EsProductDO data = new EsProductDO();
        data.setProductSn("SN00001");
        data.setName("Apple Display");
        data.setSubTitle("5K");
        data.setKeywords("apple display 5k");
        esProductRepository.save(data);
    }
}