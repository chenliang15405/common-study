package com.github;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * 文档高级查询
 *
 * @author tangsong
 * @date 2021/5/1 16:05
 */
public class EsTest_Query {

    RestHighLevelClient esClient = new RestHighLevelClient(
            RestClient.builder(new HttpHost("localhost", 9200, "http"))
    );

    @After
    public void after() throws IOException {
        esClient.close();
    }


    /**
     * 查询指定索引的所有数据
     */
    @Test
    public void match_all() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("people");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());

        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }

    /**
     * term查询， 查询条件为关键字
     */
    @Test
    public void query_term() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("people");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery("age", 10));

        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }

    /**
     * 分页查询
     */
    @Test
    public void query_page() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("people");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());

        // from指定的是页数开始的偏移量， （当前页数 - 1）* size
        builder.from(0);
        builder.size(3);

        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }

    /**
     * 数据排序
     */
    @Test
    public void query_sort() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("people");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());

        builder.sort("age", SortOrder.DESC);
        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }

    /**
     * 过滤字段
     */
    @Test
    public void query_filter() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("people");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());

        String[] excludes = {};
        String[] includes = {"name"};
        // 过滤字段，指定只包含某些字段，或者需要排除某些字段
        builder.fetchSource(includes, excludes);

        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }

    /**
     * bool查询，多条件查询 must
     */
    @Test
    public void query_bool_must() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("people");

        SearchSourceBuilder builder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // 多条件查询，must -> 一定需要匹配到
        boolQueryBuilder.must(QueryBuilders.matchQuery("age", 10));
        boolQueryBuilder.must(QueryBuilders.matchQuery("sex", "女"));


        builder.query(boolQueryBuilder);

        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }

    /**
     * bool查询，多条件查询 should
     */
    @Test
    public void query_bool_should() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("people");

        SearchSourceBuilder builder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // 多条件查询，should -> 如果当前这条数据满足两个should条件中的任意一个，则表示满足条件
        boolQueryBuilder.should(QueryBuilders.matchQuery("age", 10));
        boolQueryBuilder.should(QueryBuilders.matchQuery("sex", "女"));

        builder.query(boolQueryBuilder);

        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }

    /**
     * 范围查询
     */
    @Test
    public void query_range() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("people");

        SearchSourceBuilder builder = new SearchSourceBuilder();

        // 根据某个字段构建范围查询
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age");

        // 指定范围查询的条件数据
        rangeQueryBuilder.gte(9);
        rangeQueryBuilder.lte(12);

        builder.query(rangeQueryBuilder);

        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }

    /**
     * 模糊查询
     */
    @Test
    public void query_fuzzy() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("people");

        SearchSourceBuilder builder = new SearchSourceBuilder();

        // 模糊查询，指定字段名称进行模糊查询，后面的指定模糊匹配的字符个数
        builder.query(QueryBuilders.fuzzyQuery("name", "王五").fuzziness(Fuzziness.ONE));

        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        System.out.println("took:" + response.getTook());
        System.out.println("timeout:" + response.isTimedOut());
        System.out.println("total:" + hits.getTotalHits());
        System.out.println("MaxScore:" + hits.getMaxScore());
        System.out.println("hits========>>");
        for (SearchHit hit : hits) {
            //输出每条查询的结果信息
            System.out.println(hit.getSourceAsString());
        }
        System.out.println("<<========");
    }

    /**
     * 高亮查询
     */
    @Test
    public void query_highlight() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("people");

        SearchSourceBuilder builder = new SearchSourceBuilder();
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("sex", "女");

        // 设置查询方式
        builder.query(termsQueryBuilder);

        // 构建高亮字段
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red'>"); // 设置标签前缀
        highlightBuilder.postTags("</font>"); // 设置标签后缀
        highlightBuilder.field("sex"); // 设置高亮字段

        // 设置高亮查询
        builder.highlighter(highlightBuilder);

        // 设置请求体
        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        System.out.println(response);
        SearchHits hits = response.getHits();
        System.out.println("took::"+response.getTook());
        System.out.println("time_out::"+response.isTimedOut());
        System.out.println("total::"+hits.getTotalHits());
        System.out.println("max_score::"+hits.getMaxScore());
        System.out.println("hits::::>>");
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
            //打印高亮结果
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            System.out.println(highlightFields);
        }
    }

    /**
     * 聚合查询 —— 最大值
     */
    @Test
    public void query_aggs_max() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("people");

        SearchSourceBuilder builder = new SearchSourceBuilder();

        // 聚合查询，获取最大的age字段的数
        builder.aggregation(AggregationBuilders.max("maxAge").field("age"));

        // 设置请求体
        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    /**
     * 聚合查询—— 分组查询
     */
    @Test
    public void query_aggs_group() throws IOException {
        SearchRequest request = new SearchRequest();
        request.indices("people");

        SearchSourceBuilder builder = new SearchSourceBuilder();

        // 聚合查询，获取最大的age字段的数
        builder.aggregation(AggregationBuilders.terms("age_groupby").field("age"));

        // 设置请求体
        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

}
