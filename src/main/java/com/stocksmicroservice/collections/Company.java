package com.stocksmicroservice.collections;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@ToString
public class Company {
    private String name;
    private String description;
    private String ceo;
    private String founded;
    private String headquarters;
    private String website;
    private String employees;


    public Company(String name) {
        this.name = name;
        this.description = "description";
        this.ceo = "ceo";
        this.founded = "founded";
        this.headquarters = "headquarters";
        this.website = "website";
        this.employees = "employees";
    }
}
