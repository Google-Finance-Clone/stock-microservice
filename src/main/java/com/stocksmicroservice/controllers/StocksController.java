package com.stocksmicroservice.controllers;

import com.google.gson.JsonObject;
import com.mqconfig.MQSender;
import com.stocksmicroservice.collections.Company;
import com.stocksmicroservice.collections.Stock;
import com.stocksmicroservice.services.StocksService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@EnableRabbit
@RequestMapping("/stocks")
public class StocksController {

    private final StocksService stocksService;

    private final Object lock = new Object();
    private boolean freezeFlag = false;


    @Autowired
    MQSender mqSender;

    @Autowired
    public StocksController(StocksService stocksService) {
        this.stocksService = stocksService;
        System.out.print("checkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk" + " " + freezeFlag );

    }

    @PostMapping("/freeze")
    public boolean freeze() {
        System.out.print("checkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk" + " " + freezeFlag );

        synchronized (lock) {
            freezeFlag = true;
            stocksService.freeze();
        }
        return freezeFlag;
    }

    @PostMapping("/unfreeze")
    public boolean unfreeze() {
        System.out.print("checkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk" + " " + freezeFlag );

        synchronized (lock) {
            freezeFlag = false;
            lock.notifyAll();
            stocksService.unfreeze();
        }
        return freezeFlag;
    }

    public String sendToQueue(String message) {
        mqSender.send(message);
        return "Message sent to the RabbitMQ Successfully";
    }

    @GetMapping("/close/{ticker}/{date}")
    public double getClosePrice(@PathVariable String ticker, @PathVariable String date)
    {
        return stocksService.getCloseOnDay(ticker, date);
    }

    @GetMapping("/{ticker}")
    public Stock getCurrentPrice(@PathVariable String ticker)
    {
        return stocksService.getCurrentPrice(ticker);
    }
    @GetMapping("/{ticker}/{date}")
    public Stock getStockDataOnDate(@PathVariable String ticker, @PathVariable String date)
    {
        return stocksService.getStockDataOnDate(ticker, date);
    }

    @GetMapping("/company/{ticker}")
    public Company getCompanyData(@PathVariable String ticker)
    {
        return stocksService.getCompanyData(ticker);
    }

    @GetMapping("graph/annual/{ticker}")
    public ArrayList<Stock> getStockGraphAnnual(@PathVariable String ticker)
    {
        return stocksService.getStockGraph(ticker, "annual");
    }
    @GetMapping("graph/monthly/{ticker}")
    public ArrayList<Stock> getStockGraphMonthly(@PathVariable String ticker)
    {
        return stocksService.getStockGraph(ticker, "monthly");
    }
    @GetMapping("graph/daily/{ticker}")
    public ArrayList<Stock> getStockGraphDaily(@PathVariable String ticker)
    {
        return stocksService.getStockGraph(ticker, "daily");
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


//    @GetMapping
//    public String GetStocks() {
//        List<Stock> stocks = stocksService.getAllStocks();
//        String result = "";
//        for (Stock stock : stocks)
//            result += stock.toString();
//
//        return result;
//    }

//    create post mapping with message as an input
    @PostMapping
    public String postMessage(@RequestBody String message){
        return sendToQueue(message);
}
}
