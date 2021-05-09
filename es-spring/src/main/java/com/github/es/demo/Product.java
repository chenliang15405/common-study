package com.github.es.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author tangsong
 * @date 2021/5/1 21:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "product", shards = 3, replicas = 1) // 索引名称， 主分片数量 -> 就是将数据分配到多个分片中，不保存在一个分片（节点）， replicas：副本的数量，每个主分片都会创建这么多的副本数量
public class Product {

    //必须有id,这里的id是全局唯一的标识，等同于es中的"_id"
    @Id
    private Long id;
    /**
     * type: 字段数据类型
     * analyzer: 分词器类型
     * index: 是否索引
     * keyword: 短语，不进行分词
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;
    @Field(type = FieldType.Keyword)
    private String category;
    @Field(type = FieldType.Double)
    private Double price;
    @Field(type = FieldType.Keyword, index = false)
    private String images;
}
