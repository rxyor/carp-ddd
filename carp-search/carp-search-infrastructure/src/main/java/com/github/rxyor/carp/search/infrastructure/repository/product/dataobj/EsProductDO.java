package com.github.rxyor.carp.search.infrastructure.repository.product.dataobj;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2020/1/5 周日 22:01:00
 * @since 1.0.0
 */
@Data
@Document(indexName = "wms_product", type = "_doc", shards = 1, replicas = 0)
public class EsProductDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String productNo;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String productName;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String productTitle;

    @Field(type = FieldType.Object)
    private List<String> images;

    @Field(type = FieldType.Object)
    private List<String> tags;

    @Field(type = FieldType.Double)
    private Double minPrice;

    @Field(type = FieldType.Double)
    private Double maxPrice;

    @Field(type = FieldType.Integer)
    private Integer disable;

    @Field(type = FieldType.Date)
    private Date crateTime;

    @Field(type = FieldType.Date)
    private Date updateTime;
}
