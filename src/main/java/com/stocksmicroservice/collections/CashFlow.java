package com.stocksmicroservice.collections;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document
public class CashFlow implements Serializable {
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
