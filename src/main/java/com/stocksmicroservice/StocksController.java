package com.stocksmicroservice;

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

    @GetMapping
    public String fetchStocksFromAPI(){
       return  stocksService.fetchStockData();
    }


//    @GetMapping
//    public List<Stock> GetStocks(){
//        return stocksService.getAllStocks();
//    }
}
