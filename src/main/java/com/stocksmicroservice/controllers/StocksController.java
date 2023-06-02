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

        //{ method: (get,post,etc),
        //route: stocks/{ticker},
        //body:{if needed},
        //correlationId: notused }
        Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().create();
//        JsonObject request = gson.fromJson(message, JsonObject.class);
        JsonObject request = JsonParser.parseString(message).getAsJsonObject();

        // Check if status attribute exists in the request object

        String correlationId = request.get("correlationId").getAsString();
        String route = request.get("route").getAsString();
        String method = request.get("method").getAsString();
        method = method.toLowerCase();
        JsonObject body = request.get("body").getAsJsonObject();
         //split route by '/'
        String[] routeSplit = route.split("/");
        String response = "";
        Object responseValue = "";
        //switch case on routeSplit[1]
        switch (routeSplit[1]) {
            case "close": {
                String ticker = routeSplit[2];
                String date = routeSplit[3];
                responseValue = stocksService.getCloseOnDay(ticker, date);
                //return response as JSON
            }
            ;
            break;
            case "company": {
                String ticker = routeSplit[2];
                responseValue = stocksService.getCompanyData(ticker);
            }
            ;
            break;
            case "graph": {
                String interval = routeSplit[2];
                String ticker = routeSplit[3];
                responseValue = stocksService.getStockGraph(ticker, interval);
            }
            ;
            case "search": {
                String query = routeSplit[2];
                responseValue = searchService.searchWithQuery(query);
            };break;
            case "findById": {
                String id = routeSplit[2];
                responseValue = searchService.getSingleStockById(id);
            };
            break;
            default: {
                //It's just a ticker
                if (routeSplit.length == 2) {
                    String ticker = routeSplit[1];
                    responseValue = stocksService.getCurrentPrice(ticker);
                }
                //It's a ticker and a date
                else {
                    String ticker = routeSplit[1];
                    String date = routeSplit[2];
                    responseValue = stocksService.getStockDataOnDate(ticker, date);
                }
            }
        }
        response = new Gson().toJson(responseValue);
        return response;


//        String[] datesplit = date.split("-");
//
//        String day = datesplit[2];
//        String month = datesplit[1];
//        String annual = datesplit[0];
//
//        System.out.println("day:" + day);
//        System.out.println("month:" + month);
//        System.out.println("annual:" + annual);
//
//
//        if (method.equals("get")) {
//            if(submethod.equals("getCompanyData"))
//            {
//                Company company = stocksService.getCompanyData(ticker);
//                System.out.println("Returned Company for Company:" + company);
//                JsonObject response = new JsonObject();
//                response.addProperty("correlationId", correlationId);
//                response.addProperty("status", "success");
//                response.addProperty("body", gson.toJson(company));
//                sendToQueue(response.toString());
//            }
//            else if(submethod.equals("getClosePrice"))
//            {
//                double price = stocksService.getCloseOnDay(ticker, date);
//                System.out.println("Returned Price For Close Price:" + price);
//                JsonObject response = new JsonObject();
//                response.addProperty("correlationId", correlationId);
//                response.addProperty("status", "success");
//                response.addProperty("body", price);
//                sendToQueue(response.toString());
//            }
//            else if(submethod.equals("getCurrentPrice"))
//            {
//                Stock stock = stocksService.getCurrentPrice(ticker);
//                System.out.println("Returned Stock For Current Price:" + stock);
//                JsonObject response = new JsonObject();
//                response.addProperty("correlationId", correlationId);
//                response.addProperty("status", "success");
//                response.addProperty("body", gson.toJson(stock));
//                sendToQueue(response.toString());
//            }
//            else if(submethod.equals("getStockDataOnDate"))
//            {
//                Stock stock = stocksService.getStockDataOnDate(ticker, date);
//                System.out.println("Returned Stock For Current Price:" + stock);
//                JsonObject response = new JsonObject();
//                response.addProperty("correlationId", correlationId);
//                response.addProperty("status", "success");
//                response.addProperty("body", gson.toJson(stock));
//                sendToQueue(response.toString());
//            }
//            else if(submethod.equals("getStockGraphAnnual"))
//            {
//                ArrayList<Stock> stocks = stocksService.getStockGraph(ticker, annual);
//                System.out.println("Returned Stock List For Stock Graph Annual:" + stocks);
//                JsonObject response = new JsonObject();
//                response.addProperty("correlationId", correlationId);
//                response.addProperty("status", "success");
//                response.addProperty("body", gson.toJson(stocks));
//                sendToQueue(response.toString());
//            }
//            else if(submethod.equals("getStockGraphMonthly"))
//            {
//                ArrayList<Stock> stocks = stocksService.getStockGraph(ticker, month);
//                System.out.println("Returned Stock List For Stock Graph Monthly:" + stocks);
//                JsonObject response = new JsonObject();
//                response.addProperty("correlationId", correlationId);
//                response.addProperty("status", "success");
//                response.addProperty("body", gson.toJson(stocks));
//                sendToQueue(response.toString());
//            }
//            else if(submethod.equals("getStockGraphDaily")) {
//                ArrayList<Stock> stocks = stocksService.getStockGraph(ticker, day);
//                System.out.println("Returned Stock List For Stock Graph Daily:" + stocks);
//                JsonObject response = new JsonObject();
//                response.addProperty("correlationId", correlationId);
//                response.addProperty("status", "success");
//                response.addProperty("body", gson.toJson(stocks));
//                sendToQueue(response.toString());
//            }
//        }
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
