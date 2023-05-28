package com.stocksmicroservice.controllers;
import com.stocksmicroservice.collections.Company;
import com.stocksmicroservice.collections.Stock;
import com.stocksmicroservice.services.SearchService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/findById")
    public Optional<Stock> findStockById(@RequestParam("id") String message) {

        return searchService.getSingleStockById(message);
    }


//find stock by title or stock
    @GetMapping("/search")
    public String findByTicker(@RequestParam("query") String title) {
        List<Company> companies = searchService.searchCompany(title);
        List<Stock> stocks = new ArrayList<>();

//      for each company, get the stock data and add it to the company object
        for (Company company : companies) {
            Stock stock = searchService.getStockData(company.getTicker());
            stocks.add(stock);
        }

        return stocks.toString();
    }






}
