package com.github;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

/**
 * 文档的批量操作
 *
 * @author tangsong
 * @date 2021/5/1 15:58
 */
public class EsTest_Doc_Batch {

    RestHighLevelClient esClient = new RestHighLevelClient(
            RestClient.builder(new HttpHost("localhost", 9200, "http"))
    );

    @After
    public void after() throws IOException {
        esClient.close();
    }

    @Test
    public void doc_insert_batch() throws IOException {
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest().index("people").id("1001").source(XContentType.JSON, "name", "张三", "sex", "男", "age", 10));
        request.add(new IndexRequest().index("people").id("1002").source(XContentType.JSON, "name", "李四", "sex", "男", "age", 11));
        request.add(new IndexRequest().index("people").id("1003").source(XContentType.JSON, "name", "王五", "sex", "男", "age", 12));
        request.add(new IndexRequest().index("people").id("1004").source(XContentType.JSON, "name", "王五1", "sex", "男", "age", 8));
        request.add(new IndexRequest().index("people").id("1005").source(XContentType.JSON, "name", "王五2", "sex", "男", "age", 9));
        request.add(new IndexRequest().index("people").id("1006").source(XContentType.JSON, "name", "王五33", "sex", "男", "age", 7));

        BulkResponse response = esClient.bulk(request, RequestOptions.DEFAULT);

        System.out.println(response.getTook());
        System.out.println(Arrays.toString(response.getItems()));
    }

    @Test
    public void doc_delete_batch() throws IOException {
        BulkRequest request = new BulkRequest();
        request.add(new DeleteRequest().index("people").id("1001"));
        request.add(new DeleteRequest().index("people").id("1002"));
        request.add(new DeleteRequest().index("people").id("1003"));

        BulkResponse response = esClient.bulk(request, RequestOptions.DEFAULT);

        System.out.println(response.getTook());
        System.out.println(response);
    }

}
