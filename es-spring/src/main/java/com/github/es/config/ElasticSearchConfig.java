package com.github.es.config;

import lombok.Data;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * @author tangsong
 * @date 2021/5/1 21:05
 */
@ConfigurationProperties(prefix = "elasticsearch")
@Configuration
@Data
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

    private String host;
    private Integer port;

    /**
     * 返回ESClient对象
     */
    @Override
    public RestHighLevelClient elasticsearchClient() {
        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port));
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(builder);
        return restHighLevelClient;
    }

}
