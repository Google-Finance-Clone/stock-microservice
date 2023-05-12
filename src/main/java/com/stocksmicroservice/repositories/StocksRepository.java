package com.stocksmicroservice.repositories;

import com.stocksmicroservice.collections.Stock;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StocksRepository extends MongoRepository<Stock, String> {

    @Query(value = "{ 'Ticker': ?0, 'Date' : ?1 }")
    Stock findByTickerAndDate(String Ticker, String Date);

//    match ticker exactly, limit one, sort by date
    @Aggregation(pipeline = {
            "{$match: {Ticker: ?0}}",
            "{$sort: {date: -1}}",
            "{$limit: 1}"
    })
    List<Stock> findByTicker(String title);

    // match ticker with autocomplete
}
