package com.stocksmicroservice.services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stocksmicroservice.collections.*;
import com.stocksmicroservice.repositories.CompanyRepository;
import com.stocksmicroservice.repositories.StocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import javax.management.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class StocksService {


    private final StocksRepository stocksRepository;
    private final RestTemplate restTemplate;
    private final CompanyRepository companyRepository;
    private String today;

    private final int initialThreadCount;
    private ExecutorService threadPool;

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

    private Stock getRecentStockValue(String ticker)
    {
        return stocksRepository.findByTickerAndDate(ticker, today);
    }

    @Autowired
    public StocksService(StocksRepository stocksRepository, CompanyRepository companyRepository, RestTemplate restTemplate, @Value("${app.thread-count}") int initialThreadCount) {
        this.stocksRepository = stocksRepository;
        this.companyRepository = companyRepository;
        this.restTemplate = restTemplate;
        this.today = "2022-04-14";
        this.initialThreadCount = initialThreadCount;
        threadPool = Executors.newFixedThreadPool(initialThreadCount);
        System.out.println("valueeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee" + " " +threadPool  );
        System.out.println("2222222222222222222222222222222222222222222"+ " "+ initialThreadCount);
    }
    public void freeze() {
        // release resources
//        releaseDatabaseConnections();
        stopThreads();
    }

    public void unfreeze() {
        // acquire resources
//        acquireDatabaseConnections();
        startThreads();
    }


    public void stopThreads() {
        threadPool.shutdown();
    }

    public void startThreads() {
        threadPool = Executors.newFixedThreadPool(initialThreadCount);
    }


    public List<Stock> getAllStocks() {
        return stocksRepository.findAll();
    }

    @Cacheable(unless="#result == null", value ="stocks")
    public Stock getStockDataOnDate(String ticker, String date) {
        return stocksRepository.findByTickerAndDate(ticker, date);
    }
    @Cacheable(unless="#result == null", value ="stockCloses")
    public double getCloseOnDay(String ticker, String date) {
        Stock stock = stocksRepository.findByTickerAndDate(ticker, date);
        return stock.getClose();
    }

    @Cacheable(unless="#result == null", value ="companies")
    public Company getCompanyData(String ticker) {
        return companyRepository.findByTicker(ticker);
    }

    @Cacheable(unless="#result == null", value = "stockGraphs")
    public ArrayList<Stock> getStockGraph(String ticker, String interval) {
        String[] date = today.split("-");
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);
        ArrayList<Stock> result = new ArrayList<Stock>();
        result.add(stocksRepository.findByTickerAndDate(ticker, today));
        if(interval.equals("annual"))
        {
            for(int i = 0; i < 5; i++)
            {
                year--;
                String dayString;
                String monthString;
                if(day<10)
                {
                    dayString = "0" + day;
                }
                else {
                    dayString = "" + day;
                }
                if(month<10)
                {
                    monthString = "0" + month;
                }
                else
                {
                    monthString = "" + month;
                }
                String newDate = year + "-" + monthString + "-" + dayString;
                System.out.println(ticker + " " + newDate);
                Stock newStock = stocksRepository.findByTickerAndDate(ticker, newDate);
                System.out.println(newStock);
                if(newStock != null)
                {
                    result.add(newStock);
                }
                else
                {
                   day+=2;
                    if(day<10)
                    {
                        dayString = "0" + day;
                    }
                    else {
                        dayString = "" + day;
                    }
                    if(month<10)
                    {
                        monthString = "0" + month;
                    }
                    else
                    {
                        monthString = "" + month;
                    }
                   newDate = year + "-" + monthString + "-" + dayString;
                   newStock = stocksRepository.findByTickerAndDate(ticker, newDate);
                   if(newStock != null)
                       result.add(newStock);
                }
            }
        }
        else if(interval.equals("monthly"))
        {
            for(int i = 0; i < 12; i++)
            {
                if(month < 1)
                {
                    month = 12;
                    year--;
                }
                else
                {
                    month--;
                }
                String dayString;
                String monthString;
                if(day<10)
                {
                    dayString = "0" + day;
                }
                else {
                    dayString = "" + day;
                }
                if(month<10)
                {
                    monthString = "0" + month;
                }
                else
                {
                    monthString = "" + month;
                }
                String newDate = year + "-" + monthString + "-" + dayString;
                Stock newStock = stocksRepository.findByTickerAndDate(ticker, newDate);
                if(newStock != null)
                {
                    result.add(newStock);
                }
                else
                {
                    day+=2;
                    if(day<10)
                    {
                        dayString = "0" + day;
                    }
                    else {
                        dayString = "" + day;
                    }
                    if(month<10)
                    {
                        monthString = "0" + month;
                    }
                    else
                    {
                        monthString = "" + month;
                    }
                    newDate = year + "-" + monthString + "-" + dayString;;
                    newStock = stocksRepository.findByTickerAndDate(ticker, newDate);
                    if(newStock != null)
                        result.add(newStock);
                }
            }
        }
        else if(interval.equals("daily"))
        {
            for(int i = 0; i< 30; i++)
            {
                if(day < 1)
                {
                    day = 31;
                    if(month < 1)
                    {
                        month = 12;
                        year--;
                    }
                    else
                    {
                        month--;
                    }
                }
                else
                {
                    day--;
                }
                String dayString;
                String monthString;
                if(day<10)
                {
                    dayString = "0" + day;
                }
                else {
                    dayString = "" + day;
                }
                if(month<10)
                {
                    monthString = "0" + month;
                }
                else
                {
                    monthString = "" + month;
                }
                String newDate = year + "-" + monthString + "-" + dayString;
                Stock newStock = stocksRepository.findByTickerAndDate(ticker, newDate);
                if(newStock != null)
                {
                    result.add(newStock);
                }
                else
                {
                    day+=2;
                    if(day<10)
                    {
                        dayString = "0" + day;
                    }
                    else {
                        dayString = "" + day;
                    }
                    if(month<10)
                    {
                        monthString = "0" + month;
                    }
                    else
                    {
                        monthString = "" + month;
                    }
                    newDate = year + "-" + month + "-" + day;
                    newStock = stocksRepository.findByTickerAndDate(ticker, newDate);
                    if(newStock != null)
                        result.add(newStock);
                }
            }
        }
        return result;
    }

    @Cacheable(unless="#result == null", value ="stockPrices")
    public Stock getCurrentPrice(String ticker) {
        return stocksRepository.findByTickerAndDate(ticker, today);
    }
}
