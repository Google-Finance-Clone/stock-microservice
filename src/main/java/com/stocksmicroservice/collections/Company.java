package com.stocksmicroservice.collections;

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

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                '}';
    }
}
