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

//    match stock with autocomplete
    @Aggregation(pipeline = {"{$match: {stock: {$regex: ?0, $options: 'i'}}}"})
    List<Stock> findByStock(String stock);


//    find any relevant stock based on either title or stock attribute
    @Aggregation(pipeline = {"{$match: {$or: [{title: {$regex: ?0, $options: 'i'}}, {stock: {$regex: ?0, $options: 'i'}}]}}"})
    List<Stock> findByTitleOrStock(String title, String stock);
}
