package com.stocksmicroservice.repositories;

import com.stocksmicroservice.collections.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StocksRepository extends MongoRepository<Stock, String> {


}
