package com.github.rxyor.carp.search.infrastructure.repository.product.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.github.rxyor.carp.search.SpringWithJUnit5IT;
import com.github.rxyor.carp.search.infrastructure.repository.product.dataobj.EsProductDO;
import org.assertj.core.util.Lists;
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
    public void findByNaAndNameOrSubTitleOrKeywords() {
        Iterator<EsProductDO> iter = esProductRepository.findAll().iterator();
        List<EsProductDO> list = new ArrayList<>(16);
        while (iter.hasNext()) {
            list.add(iter.next());
        }
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
}