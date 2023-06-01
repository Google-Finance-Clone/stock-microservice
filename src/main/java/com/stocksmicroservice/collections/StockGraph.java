package com.stocksmicroservice.collections;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document
public class StockGraph implements Serializable {
    private int price;
    private String currency;
    private String data;
    private int volume;
}
