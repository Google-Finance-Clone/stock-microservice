package com.stocksmicroservice.collections;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@Document(collection = "companies")
public class Company implements Serializable {
    @Id
    private String id;
    private String ticker;
    @Field("company name")
    private String companyName;
    @Field("short name")
    private String shortName;
    private String industry;
    private String description;
    private String website;
    private String logo;
    private String ceo;
    private String exchange;
    @Field("market cap")
    private long marketCap;
    private String sector;

    public Company(String ticker, String companyName, String shortName, String industry, String description, String website, String logo, String ceo, String exchange, long marketCap, String sector) {
        this.ticker = ticker;
        this.companyName = companyName;
        this.shortName = shortName;
        this.industry = industry;
        this.description = description;
        this.website = website;
        this.logo = logo;
        this.ceo = ceo;
        this.exchange = exchange;
        this.marketCap = marketCap;
        this.sector = sector;
    }

    @Override
    public String toString() {
        return "Company{" +
                "ticker='" + ticker + '\'' +
                ", companyName='" + companyName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", industry='" + industry + '\'' +
                ", description='" + description + '\'' +
                ", website='" + website + '\'' +
                ", logo='" + logo + '\'' +
                ", ceo='" + ceo + '\'' +
                ", exchange='" + exchange + '\'' +
                ", marketCap=" + marketCap +
                ", sector='" + sector + '\'' +
                '}';
    }
}
