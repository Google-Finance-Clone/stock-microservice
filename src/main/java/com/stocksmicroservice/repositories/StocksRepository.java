package com.stocksmicroservice.repositories;

import com.stocksmicroservice.collections.Stock;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StocksRepository extends MongoRepository<Stock, String> {

    @Aggregation(pipeline = {
            "{$search: {text: {query: ?0, path: 'title'}}}",
            "{$project: {_id: 0}}",
            "{$limit: 10}"
    })
    List<Stock> findByTitle(String title);
}
