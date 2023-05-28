package com.stocksmicroservice.collections;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class CashFlow {
    private String netIncome;
    private String cashFromOperations;
    private String cashFromInvesting;
    private String cashFromFinancing;
    private String netChangeInCash;
    private String freeCashFlow;
    private String data;

    public CashFlow() {
    }

}
