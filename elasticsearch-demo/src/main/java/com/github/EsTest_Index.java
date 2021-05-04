package com.github;

import com.sun.org.glassfish.external.statistics.annotations.Reset;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

/**
 * 索引操作
 *
 * @author tangsong
 * @date 2021/5/1 15:08
 */
public class EsTest_Index {

    RestHighLevelClient esClient = new RestHighLevelClient(
            RestClient.builder(new HttpHost("localhost", 9200, "http"))
    );

    @After
    public void after() throws IOException {
        esClient.close();
    }

    @Test
    public void index_create() throws IOException {
        // 创建索引 - 请求对象
        CreateIndexRequest request = new CreateIndexRequest("test");
        // 发送请求，获取响应
        CreateIndexResponse response = esClient.indices().create(request, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        // 响应状态
        System.out.println("操作状态 = " + acknowledged);
    }

    @Test
    public void index_get() throws IOException {

        GetIndexRequest request = new GetIndexRequest("test");

        GetIndexResponse response = esClient.indices().get(request, RequestOptions.DEFAULT);

        System.out.println(Arrays.toString(response.getIndices()));
        System.out.println(response.getMappings());
        System.out.println(response.getSettings());
    }

    @Test
    public void index_delete() throws IOException {

        DeleteIndexRequest request = new DeleteIndexRequest("test");

        AcknowledgedResponse response = esClient.indices().delete(request, RequestOptions.DEFAULT);

        System.out.println(response.isAcknowledged());
    }

}
