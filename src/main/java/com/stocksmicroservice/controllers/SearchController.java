package com.stocksmicroservice.controllers;
import com.stocksmicroservice.collections.Stock;
import com.stocksmicroservice.services.SearchService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
@RestController
@EnableRabbit
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/findById")
    public Optional<Stock> findStockById(@RequestParam("id") String message) {
        Optional<Stock> singleStock = searchService.getSingleStockById(message);

        return singleStock;
    }


//    @GetMapping("/search")
//    public String searchStock(@RequestParam("id") String message) {
//        // List<Stock> stocks = stocksService.getFilteredStocks(message);
//
//        List<Stock> stocks = searchService.getFilteredStocksByName(message);
//
//        String result = "";
//        for (Stock stock : stocks)
//            result += stock.toString();
//
//        return result;
//    }




}
