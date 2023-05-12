package com.stocksmicroservice.services;


import com.stocksmicroservice.collections.Stock;
import com.stocksmicroservice.repositories.StocksRepository;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearchService {

    private final StocksRepository stocksRepository;

    public SearchService(StocksRepository stocksRepository) {
        this.stocksRepository = stocksRepository;
    }



    public Optional<Stock> getSingleStockById(String Id ) {
        return stocksRepository.findById(Id);
    }

//    get stock by title
    public List<Stock> getStockByTitle(String title ) {
        return stocksRepository.findByTitle(title);


    }


}
