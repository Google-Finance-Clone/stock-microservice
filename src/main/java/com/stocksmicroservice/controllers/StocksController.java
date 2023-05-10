package com.stocksmicroservice.controllers;

import com.google.gson.JsonObject;
import com.mqconfig.MQSender;
import com.stocksmicroservice.collections.Stock;
import com.stocksmicroservice.services.StocksService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.List;

@RestController
@EnableRabbit
@RequestMapping("/stocks")
public class StocksController {

    private final StocksService stocksService;

    @Autowired
    MQSender mqSender;

    @Autowired
    public StocksController(StocksService stocksService) {
        this.stocksService = stocksService;
    }


    public String sendToQueue(String message) {
        mqSender.send(message);
        return "Message sent to the RabbitMQ Successfully";
    }

//    @GetMapping
//    public String fetchStocksFromAPI(){
//       return stocksService.getElementsFoDB();
//        //return  stocksService.fetchStockData();
//    }

//    @GetMapping
//    public void addNewStock(){
//        stocksService.addNewStock();
//    }


    @GetMapping
    public String GetStocks() {
        List<Stock> stocks = stocksService.getAllStocks();
        String result = "";
        for (Stock stock : stocks)
            result += stock.toString();

        return result;
    }

//    create post mapping with message as an input
    @PostMapping
    public String postMessage(@RequestBody String message){
        return sendToQueue(message);
}
}
