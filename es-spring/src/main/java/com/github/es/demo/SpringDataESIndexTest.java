package com.github.es.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author tangsong
 * @date 2021/5/1 21:12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataESIndexTest {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    public void createIndex() {
        // 创建索引，spring容器启动就会创建索引，因为Product已经配置索引映射
        System.out.println("自动创建索引");
    }

    @Test
    public void deleteIndex() {
        boolean deleteIndex = elasticsearchRestTemplate.deleteIndex(Product.class);
        System.out.println("删除索引result:  " + deleteIndex);
    }
}
