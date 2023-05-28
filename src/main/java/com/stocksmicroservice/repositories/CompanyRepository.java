package com.stocksmicroservice.repositories;

import com.stocksmicroservice.collections.Company;
import com.stocksmicroservice.collections.Stock;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {
    @Query(value = "{'Ticker': ?0}")
    Company findByTicker(String Ticker);

//    find by 'ticker' or 'company name' use autocomplete
    @Aggregation(pipeline = {
            "{$match: {$or: [{ticker: {$regex: ?0, $options: 'i'}}, {companyName: {$regex: ?0, $options: 'i'}}]}}",
            "{$sort: {ticker: 1}}",
            "{$limit: 10}"
    })
    List<Company> findByTickerOrCompanyName(String Ticker, String companyName);
}
