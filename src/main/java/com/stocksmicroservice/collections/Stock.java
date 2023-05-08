package com.stocksmicroservice.collections;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Stock {
    @Id
    private String id;
    private String title;
    private String stock;
    private String exchange;
    private String price;
    private String[] extensions;
    private StockGraph[] graph;
    private String[] tags;
    private String previousClose;
    private String dayRange;
    private String yearRange;
    private String marketCap;
    private String avgVolume;
    private String peRatio;
    private String dividendYield;
    private String primaryExchange;
    private Company companyInfo;
    private BalanceSheet balanceSheet;
    private CashFlow cashFlow;
    private IncomeStatement incomeStatement;


    public Stock(String title, String stock, String exchange, String price, String[] extensions, StockGraph[] graph, String[] tags, String previousClose, String dayRange, String yearRange, String marketCap, String avgVolume, String peRatio, String dividendYield, String primaryExchange, Company companyInfo, BalanceSheet balanceSheet, CashFlow cashFlow, IncomeStatement incomeStatement) {
        this.title = title;
        this.stock = stock;
        this.exchange = exchange;
        this.price = price;
        this.extensions = extensions;
        this.graph = graph;
        this.tags = tags;
        this.previousClose = previousClose;
        this.dayRange = dayRange;
        this.yearRange = yearRange;
        this.marketCap = marketCap;
        this.avgVolume = avgVolume;
        this.peRatio = peRatio;
        this.dividendYield = dividendYield;
        this.primaryExchange = primaryExchange;
        this.companyInfo = companyInfo;
        this.balanceSheet = balanceSheet;
        this.cashFlow = cashFlow;
        this.incomeStatement = incomeStatement;
    }
}
