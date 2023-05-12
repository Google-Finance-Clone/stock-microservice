package com.stocksmicroservice.repositories;

import com.stocksmicroservice.collections.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface StocksRepository extends MongoRepository<Stock, String> {
    @Query(value = "{ 'Ticker': ?0, 'Date' : ?1 }")
    Stock findByTickerAndDate(String Ticker, String Date);
    @Query(value = "{'Ticker': ?0}")
    Stock findByTicker(String Ticker);
}
