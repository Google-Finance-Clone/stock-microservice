package com.stocksmicroservice.repositories;

import com.stocksmicroservice.collections.Stock;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StocksRepository extends MongoRepository<Stock, String> {

// match title with autocomplete
    @Aggregation(pipeline = {"{$match: {title: {$regex: ?0, $options: 'i'}}}"})
    List<Stock> findByTitle(String title);
}
