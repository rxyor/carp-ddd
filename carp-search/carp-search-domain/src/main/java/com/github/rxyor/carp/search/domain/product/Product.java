package com.github.rxyor.carp.search.domain.product;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 *<p>
 * 产品领域对象
 *</p>
 *
 * @author qianmu.ly
 * @since 2021-01-18 11:17:14 v1.0
 */
@Data
public class Product {

    private Long id;

    private String productNo;

    private String productName;

    private String productTitle;

    private List<String> images;

    private List<String> tags;

    private Double minPrice;

    private Double maxPrice;

    private Integer disable;

    private Date crateTime;

    private Date updateTime;
}
