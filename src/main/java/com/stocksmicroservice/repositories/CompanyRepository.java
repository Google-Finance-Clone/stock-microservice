package com.stocksmicroservice.repositories;

import com.stocksmicroservice.collections.Company;
import com.stocksmicroservice.collections.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {
    @Query(value = "{'Ticker': ?0}")
    Company findByTicker(String Ticker);
}
