package com.stocksmicroservice.services;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.google.gson.JsonObject;
import com.stocksmicroservice.collections.Stock;
import com.stocksmicroservice.repositories.StocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;





import java.util.List;

@Service
public class StocksService {


    private final StocksRepository stocksRepository;
    private final RestTemplate restTemplate;
    private String apiLink;
    private String apiKey;

    private void getAllTickers()
    {
        String url = "https://api.polygon.io/v3/reference/tickers?market=stocks&active=true&limit=1000&apiKey=qcVQuyN5MfYsyq_nuJil83LIoWXDepPo";
        JsonObject response = restTemplate.getForObject(url, JsonObject.class);
        JsonObject results = response.get("results").getAsJsonObject();
        for (JsonElement stockElement: results.getAsJsonArray())
        {
            JsonObject stockJson = stockElement.getAsJsonObject();

        }

    }

    public void fetchStockData(){

        String url = "https://api.polygon.io/v1/open-close/TSLA/2023-01-09?adjusted=true&";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(apiKey);
//        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
//        String response = restTemplate.getForObject(url, String.class,requestEntity);
        url+="apikey=" + apiKey;
        String resposne = restTemplate.getForObject(url, String.class);
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
