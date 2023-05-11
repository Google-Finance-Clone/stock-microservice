package com.stocksmicroservice.collections;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class StockGraph {
    private int price;
    private String currency;
    private String data;
    private int volume;
}
