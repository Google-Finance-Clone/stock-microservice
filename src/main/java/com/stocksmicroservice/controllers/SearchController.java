package com.stocksmicroservice.controllers;
import com.stocksmicroservice.collections.Company;
import com.stocksmicroservice.collections.Stock;
import com.stocksmicroservice.services.SearchService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RestController
@EnableRabbit
@RequestMapping("/stocks")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/findById/{id}")
    public Optional<Stock> findStockById(@PathVariable String id) {

        return searchService.getSingleStockById(id);
    }

//find stock by title or stock
    @GetMapping("/search/{query}")
    public List<Stock> findByTicker(@PathVariable String query) {
        return searchService.searchWithQuery(query);
    }






}
