package com.stocksmicroservice;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@Service
public class StocksService {


    private final StocksRepository stocksRepository;
    private final RestTemplate restTemplate;
    private String apiLink;
    private String apiKey;

    public String fetchStockData(){
        String url = "https://api.polygon.io/v1/open-close/TSLA/2023-01-09?adjusted=true&";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(apiKey);
//        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
//        String response = restTemplate.getForObject(url, String.class,requestEntity);
        url+="apikey=" + apiKey;
        String resposne = restTemplate.getForObject(url, String.class);
        return resposne;
    }

    @Autowired
    public StocksService(StocksRepository stocksRepository, RestTemplate restTemplate) {
        this.stocksRepository = stocksRepository;
        this.restTemplate = restTemplate;
        this. apiLink = "https://api.polygon.io/";
        this.apiKey = "qcVQuyN5MfYsyq_nuJil83LIoWXDepPo";
    }

    public List<Stock> getAllStocks() {
        return stocksRepository.findAll();
    }
}
