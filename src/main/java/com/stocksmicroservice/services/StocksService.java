package com.stocksmicroservice.services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stocksmicroservice.collections.*;
import com.stocksmicroservice.repositories.StocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@Service
public class StocksService {


    private final StocksRepository stocksRepository;
    private final RestTemplate restTemplate;
    private String apiLink;
    private String apiKey;

    public String fetchStockData() {
        //   String url = "https://api.polygon.io/v1/open-close/TSLA/2023-01-09?adjusted=true&";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(apiKey);
//        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
//        String response = restTemplate.getForObject(url, String.class,requestEntity);
        //    url+="apikey=" + apiKey;
        String url = "https://serpapi.com/search.json?apikey=7ba471a4862d7a11e51ebc0668c2b38e255bfb24c403f5d0fbd54d5dd287fb93&engine=google_finance&q=GOOG:NASDAQ";
        String resposne = restTemplate.getForObject(url, String.class);
        JsonObject jsonObject = JsonParser.parseString(resposne).getAsJsonObject();
        //  JsonObject resposne = restTemplate.getForObject(url, JsonObject.class);

        return jsonObject.get("summary").toString();
    }

    @Autowired
    public StocksService(StocksRepository stocksRepository, RestTemplate restTemplate) {
        this.stocksRepository = stocksRepository;
        this.restTemplate = restTemplate;
        this.apiLink = "https://api.polygon.io/";
        this.apiKey = "qcVQuyN5MfYsyq_nuJil83LIoWXDepPo";
    }

    public String getElementsFoDB() {
        String result = stocksRepository.findAll().toString();
        System.out.println(result);
        return result;

    }

    public List<Stock> getAllStocks() {
        return stocksRepository.findAll();
    }

    public void addNewStock() {
        Stock newStock = new Stock("TST", "Test", "NASSDAQ", "40", new String[1], new StockGraph[1], new String[1], "30", "day", "year", "10000", "40000", "0.2", "0.4", "NASDAQ", new Company("test"), new BalanceSheet(), new CashFlow(), new IncomeStatement());
        stocksRepository.insert(newStock);
    }
}
