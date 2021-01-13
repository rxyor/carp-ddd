package com.github.rxyor.carp.search.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 *<p>
 *
 *</p>
 *
 * @author qianmu.ly
 * @since 2021-01-11 17:48:45 v1.0
 */
@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {

    @Value("#{'${elasticsearch.hosts}'.split(',')}")
    private String[] hosts;

    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
            .connectedTo(hosts)
            .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
