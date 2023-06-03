package com.stocksmicroservice.collections;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document
public class BalanceSheet implements Serializable {
    private String CashAndShortTermInvestments;
    private String totalAssets;
    private String totalLiabilities;
    private String totalEquity;
    private String sharesOutstanding;
    private String priceToBook;
    private String returnOnAssets;
    private String returnOnCapital;

    public BalanceSheet() {
    }

    @Override
    public String toString() {
        return "BalanceSheet{}";
    }
}
