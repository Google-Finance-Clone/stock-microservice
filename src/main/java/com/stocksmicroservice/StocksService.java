package com.stocksmicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StocksService {

    private final StocksRepository stocksRepository;

    @Autowired
    public StocksService(StocksRepository stocksRepository) {
        this.stocksRepository = stocksRepository;
    }

    public List<Stock> getAllStocks() {
        return stocksRepository.findAll();
    }
}
