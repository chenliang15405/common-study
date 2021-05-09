package com.github.es.demo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author tangsong
 * @date 2021/5/1 21:07
 */
@Repository
public interface ProductDao extends ElasticsearchRepository<Product, Long> {
}
