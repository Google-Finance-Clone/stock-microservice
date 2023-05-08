package com.stocksmicroservice.repositories;

import com.stocksmicroservice.collections.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompaniesRepository extends MongoRepository<Company, String> {
}
