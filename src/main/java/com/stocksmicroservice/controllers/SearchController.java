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

        return searchService.getSingleStockById(message);
    }



//find stock by title
    @GetMapping("/findByTitle")
    public String findStockByTitle(@RequestParam("title") String message) {
        List<Stock> stocks = searchService.getStockByTitle(message);

        return stocks.toString();
    }






}
