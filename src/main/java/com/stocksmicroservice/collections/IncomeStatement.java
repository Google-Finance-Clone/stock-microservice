package com.stocksmicroservice.collections;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class IncomeStatement {
    private String revenue;
    private String operatingExpense;
    private String netIncome;
    private String netProfitMargin;
    private String earningsPerShare;
    private String ebitda;
    private String effectiveTaxRate;
    private String data;

    public IncomeStatement() {
    }
}
