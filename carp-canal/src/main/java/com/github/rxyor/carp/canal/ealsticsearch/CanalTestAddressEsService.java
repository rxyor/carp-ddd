package com.github.rxyor.carp.canal.ealsticsearch;

import com.alibaba.fastjson.JSON;
import com.github.rxyor.carp.canal.message.processor.canal.CanalTestDO;
import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class CanalTestAddressEsService {

    private final RestHighLevelClient restHighLevelClient;

    public void save(List<CanalTestDO> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (CanalTestDO data : list) {
            try {
                IndexRequest request = new IndexRequest("put");
                request.index("address")
                    .id(data.getId().toString())
                    .source(JSON.toJSONString(data), XContentType.JSON);
                IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
                log.info("response:{}", JSON.toJSONString(response));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

}
