package com.github.rxyor.carp.search.infrastructure.repository.product.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.rxyor.carp.search.SpringWithJUnit5IT;
import com.github.rxyor.carp.search.infrastructure.repository.product.dataobj.EsProductDO;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    public void findAll() {
        Iterable<EsProductDO> iterable = esProductRepository.findAll();
        final List<EsProductDO> list = new ArrayList<>();
        iterable.forEach(list::add);
        log.info("result:{}", list);
    }

    @Test
    void save() {
        EsProductDO dagaObj = new EsProductDO();
        dagaObj.setId(System.currentTimeMillis());
        dagaObj.setProductNo("SN" + dagaObj.getId());
        dagaObj.setProductName("Apple Display");
        dagaObj.setProductTitle("【Apple Display】5K Display显示器");
        dagaObj.setImages(Lists.newArrayList(
            "https://images.contentstack.io/v3/assets/bltefdd0b53724fa2ce/blt280217a63b82a734"
                + "/5bbdaacf63ed239936a7dd56/elastic-logo.svg"));
        dagaObj.setTags(Lists.newArrayList("苹果", "显示器", "5K"));
        dagaObj.setMinPrice(5999D);
        dagaObj.setMaxPrice(10999D);
        dagaObj.setDisable(0);
        dagaObj.setCrateTime(new Date());
        esProductRepository.save(dagaObj);
    }

    @Test
    void findByProductNoOrProductNameOrProductTitle() {
        Pageable pageable = PageRequest.of(1, 2);
        Page<EsProductDO> result = esProductRepository.findByProductNoOrProductNameOrProductTitle("1610528045009",
            null, null, pageable);
        log.info("result:{}", result.getContent());
    }
}