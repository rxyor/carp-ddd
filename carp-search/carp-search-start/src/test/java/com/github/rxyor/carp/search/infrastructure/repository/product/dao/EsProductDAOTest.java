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
public class EsProductDAOTest extends SpringWithJUnit5IT {

    @Autowired
    EsProductDAO esProductDAO;

    @Test
    public void findAll() {
        Iterable<EsProductDO> iterable = esProductDAO.findAll();
        final List<EsProductDO> list = new ArrayList<>();
        iterable.forEach(list::add);
        log.info("result:{}", list);
    }

    @Test
    void save() {
        EsProductDO dataObj = new EsProductDO();
        dataObj.setId(System.currentTimeMillis());
        dataObj.setProductNo("SN" + dataObj.getId());
        dataObj.setProductName("Apple Display");
        dataObj.setProductTitle("【Apple Display】5K Display显示器");
        dataObj.setImages(Lists.newArrayList(
            "https://images.contentstack.io/v3/assets/bltefdd0b53724fa2ce/blt280217a63b82a734"
                + "/5bbdaacf63ed239936a7dd56/elastic-logo.svg"));
        dataObj.setTags(Lists.newArrayList("苹果", "显示器", "5K"));
        dataObj.setMinPrice(5999D);
        dataObj.setMaxPrice(10999D);
        dataObj.setDisable(0);
        dataObj.setCrateTime(new Date());
        esProductDAO.save(dataObj);
    }

    @Test
    void saveV2() {
        EsProductDO dataObj = new EsProductDO();
        dataObj.setId(1L);
        dataObj.setProductNo("SN" + dataObj.getId());
        dataObj.setProductName("Apple Display");
        dataObj.setProductTitle("【Apple Display】4K Display显示器");
        dataObj.setImages(Lists.newArrayList(
            "https://images.contentstack.io/v3/assets/bltefdd0b53724fa2ce/blt280217a63b82a734"
                + "/5bbdaacf63ed239936a7dd56/elastic-logo.svg"));
        dataObj.setTags(Lists.newArrayList("苹果", "显示器", "4K"));
        dataObj.setMinPrice(5999D);
        dataObj.setMaxPrice(10999D);
        dataObj.setDisable(0);
        dataObj.setCrateTime(new Date());
        esProductDAO.save(dataObj);
    }

    @Test
    void findByProductNoOrProductNameOrProductTitle() {
        Pageable pageable = PageRequest.of(1, 2);
        Page<EsProductDO> result = esProductDAO.findByProductNoOrProductNameOrProductTitle("1610528045009",
            null, null, pageable);
        log.info("result:{}", result.getContent());
    }
}