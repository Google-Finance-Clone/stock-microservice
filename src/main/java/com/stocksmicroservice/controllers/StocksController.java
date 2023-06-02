package com.stocksmicroservice.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mqconfig.MQSender;
import com.stocksmicroservice.collections.Company;
import com.stocksmicroservice.collections.Stock;
import com.stocksmicroservice.services.SearchService;
import com.stocksmicroservice.services.StocksService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    private final SearchService searchService;

    @Autowired
    MQSender mqSender;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public StocksController(StocksService stocksService, SearchService searchService) {
        this.stocksService = stocksService;
        this.searchService = searchService;
    }

    public void sendToQueue(String message) {
        rabbitTemplate.convertAndSend("stocksQueue", message);
    }


    @PostMapping("/testRabbitMQ")
    public String postMessage(@RequestBody String message){
        System.out.println("message sent");
        Gson gson = new Gson();
        JsonObject messageObject = gson.fromJson(message, JsonObject.class);
        sendToQueue(messageObject.toString());
        return "Message sent to the RabbitMQ Successfully";
    }

    @RabbitListener(queues = "stocksSender")
    public String receiveMessage(String message) {

        System.out.println("message received");

        //{ method: (get,post,etc),
        //route: stocks/{ticker},
        //body:{if needed},
        //correlationId: notused }

        Gson gson = new Gson();

//        remove first and last character from message
        message = message.replace("\\", "");

        JsonObject request = gson.fromJson(message, JsonObject.class);

        // Check if status attribute exists in the request object

        String correlationId = request.get("correlationId").getAsString();
        String route = request.get("route").getAsString();
        String method = request.get("method").getAsString();
        method = method.toLowerCase();
        JsonObject body = request.get("body").getAsJsonObject();
         //split route by '/'
        String[] routeSplit = route.split("/");
        String response = "";
        String responseValue = "";
        //switch case on routeSplit[1]
        switch (routeSplit[1]) {
            case "close": {
                String ticker = routeSplit[2];
                String date = routeSplit[3];
                double res = stocksService.getCloseOnDay(ticker, date);
                responseValue = String.valueOf(res);
                return responseValue;
                //return response as JSON
            }
            case "company": {
                String ticker = routeSplit[2];
                Company res = stocksService.getCompanyData(ticker);
                responseValue = gson.toJson(res);
                return responseValue;
            }
            case "graph": {
                String interval = routeSplit[2];
                String ticker = routeSplit[3];
                responseValue = gson.toJson(stocksService.getStockGraph(ticker, interval));
                return responseValue;
            }
            case "search": {
                String query = routeSplit[2];
                responseValue = gson.toJson(searchService.searchWithQuery(query));
                return responseValue;
            }
            case "findById": {
                String id = routeSplit[2];
                responseValue = gson.toJson(searchService.getSingleStockById(id));
                return responseValue;
            }
            default: {
                //It's just a ticker
                if (routeSplit.length == 2) {
                    System.out.println("You Are Here");
                    String ticker = routeSplit[1];
                    responseValue = gson.toJson(stocksService.getCurrentPrice(ticker));
                    System.out.println("The response value is "+ responseValue);
//                    print the type of the response value
                    System.out.println("The type of the response value is "+ responseValue.getClass().getName());
                    return responseValue;
                }
                //It's a ticker and a date
                else {
                    String ticker = routeSplit[1];
                    String date = routeSplit[2];
                    responseValue = gson.toJson(stocksService.getStockDataOnDate(ticker, date));
                    return responseValue;
                }
            }
        }
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
//    @PostMapping
//    public String postMessage(@RequestBody String message){
//        return sendToQueue(message);
//}
}
