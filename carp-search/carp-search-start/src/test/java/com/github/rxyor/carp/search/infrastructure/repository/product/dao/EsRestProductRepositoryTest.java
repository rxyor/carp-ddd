package com.github.rxyor.carp.search.infrastructure.repository.product.dao;

import java.io.IOException;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;

import com.github.rxyor.carp.search.SpringWithJUnit5IT;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;

/**
 *<p>
 *
 *</p>
 *
 * @author qianmu.ly
 * @since 2021-01-11 20:12:38 v1.0
 */
public class EsRestProductRepositoryTest extends SpringWithJUnit5IT {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void testCreateIndex() {
        final String index = "wms_product";

        String mapping = "{\n"
            + "        \"properties\": {\n"
            + "            \"id\": {\n"
            + "                \"type\": \"long\"\n"
            + "            },\n"
            + "            \"productNo\": {\n"
            + "                \"type\": \"text\",\n"
            + "                \"fields\": {\n"
            + "                    \"keyword\": {\n"
            + "                        \"type\": \"keyword\",\n"
            + "                        \"ignore_above\": 256\n"
            + "                    }\n"
            + "                }\n"
            + "            },\n"
            + "            \"productName\": {\n"
            + "                \"type\": \"text\",\n"
            + "                \"fields\": {\n"
            + "                    \"keyword\": {\n"
            + "                        \"type\": \"keyword\",\n"
            + "                        \"ignore_above\": 256\n"
            + "                    }\n"
            + "                }\n"
            + "            },\n"
            + "            \"productTitle\": {\n"
            + "                \"type\": \"text\",\n"
            + "                \"fields\": {\n"
            + "                    \"keyword\": {\n"
            + "                        \"type\": \"keyword\",\n"
            + "                        \"ignore_above\": 256\n"
            + "                    }\n"
            + "                }\n"
            + "            },\n"
            + "            \"images\": {\n"
            + "                \"properties\": []\n"
            + "            },\n"
            + "            \"tags\": {\n"
            + "                \"properties\": []\n"
            + "            },\n"
            + "            \"minPrice\": {\n"
            + "                \"type\": \"double\"\n"
            + "            },\n"
            + "            \"maxPrice\": {\n"
            + "                \"type\": \"double\"\n"
            + "            },\n"
            + "            \"disable\": {\n"
            + "                \"type\": \"integer\"\n"
            + "            },\n"
            + "            \"createTime\": {\n"
            + "                \"type\": \"date\"\n"
            + "            },\n"
            + "            \"updateTime\": {\n"
            + "                \"type\": \"date\"\n"
            + "            }\n"
            + "        }\n"
            + "    }";

        CreateIndexRequest request = new CreateIndexRequest(index);
        Settings.Builder settings = Settings.builder()
            .put("index.number_of_shards", 3)
            .put("index.number_of_replicas", 2);
        request.settings(settings);
        request.mapping(mapping, XContentType.JSON);

        try {
            GetIndexRequest getIndexRequest = new GetIndexRequest(index);
            boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
            if (exists) {
                throw new RuntimeException(String.format("[%s]已经存在", index));
            }

            CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            if (!Boolean.TRUE.equals(response.isAcknowledged())) {
                throw new RuntimeException(JSON.toJSONString(response));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}