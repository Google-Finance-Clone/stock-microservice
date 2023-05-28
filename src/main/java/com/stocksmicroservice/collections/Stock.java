package com.stocksmicroservice.collections;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Arrays;
import java.util.Date;

@Data
@Document(collection = "stocks")
public class Stock {
    @Id
    private String id;
    private String Ticker;
    private String Date;
    private double High;
    private double Low;
    private double Open;
    private double Close;
    private long Volume;
    @Field("Adj Close")
    private double AdjClose;

    public Stock(String Ticker, String Date, double High, double Low, double Open, double Close, long Volume, double AdjClose) {
        this.Ticker = Ticker;
        this.Date = Date;
        this.High = High;
        this.Low = Low;
        this.Open = Open;
        this.Close = Close;
        this.Volume = Volume;
        this.AdjClose = AdjClose;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "Ticker='" + Ticker + '\'' +
                ", Date='" + Date + '\'' +
                ", High=" + High +
                ", Low=" + Low +
                ", Open=" + Open +
                ", Close=" + Close +
                ", volume=" + Volume +
                ", AdjClose=" + AdjClose +
                '}';
    }
}
