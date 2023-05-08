package com.stocksmicroservice.controller;

import com.stocksmicroservice.services.StocksService;
import com.stocksmicroservice.collections.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StocksController {

    private final StocksService stocksService;

    @Autowired
    public StocksController(StocksService stocksService) {
        this.stocksService = stocksService;
    }

    @PostMapping
    public void fetchStocksFromAPI(){
       stocksService.fetchStockData();
    }


    @GetMapping
    public List<Stock> GetStocks(){
        return stocksService.getAllStocks();
    }
}
