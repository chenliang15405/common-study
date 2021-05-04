package com.github;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.Index;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.lang.ref.ReferenceQueue;

/**
 * 文档操作
 *
 * @author tangsong
 * @date 2021/5/1 15:18
 */
public class EsTest_Doc {

    RestHighLevelClient esClient = new RestHighLevelClient(
            RestClient.builder(new HttpHost("localhost", 9200, "http"))
    );

    @After
    public void after() throws IOException {
        esClient.close();
    }

    @Test
    public void doc_create() throws IOException {
        IndexRequest request = new IndexRequest();
        // 设置操作的索引和当前插入的文档的id
        request.index("people").id("1001");

        User user = new User();
        user.setName("张三");
        user.setAge(10);
        user.setSex("男");

        ObjectMapper mapper = new ObjectMapper();
        String userJson = mapper.writeValueAsString(user);

        request.source(userJson, XContentType.JSON);

        IndexResponse response = esClient.index(request, RequestOptions.DEFAULT);

        System.out.println(response);

        System.out.println("_index" + response.getIndex());
        System.out.println("_id" + response.getId());
        System.out.println("_result" + response.getResult());
    }


    @Test
    public void doc_update() throws IOException {
        UpdateRequest request = new UpdateRequest();
        request.index("people").id("1001");

        request.doc(XContentType.JSON, "sex", "女");

        UpdateResponse response = esClient.update(request, RequestOptions.DEFAULT);

        System.out.println(response);
    }

    @Test
    public void doc_get() throws IOException {

        GetRequest request = new GetRequest();
        request.index("people").id("1001");

        GetResponse response = esClient.get(request, RequestOptions.DEFAULT);

        System.out.println(response);
        System.out.println(response.getSource());
    }

    @Test
    public void doc_delete() throws IOException {
        DeleteRequest request = new DeleteRequest();
        request.index("people").id("1001");

        DeleteResponse response = esClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

}
