package com.stocksmicroservice.services;


import com.stocksmicroservice.collections.Company;
import com.stocksmicroservice.collections.Stock;
import com.stocksmicroservice.repositories.CompanyRepository;
import com.stocksmicroservice.repositories.StocksRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearchService {

    private final StocksRepository stocksRepository;
    private final CompanyRepository companyRepository;

    public SearchService(StocksRepository stocksRepository, CompanyRepository companyRepository) {
        this.stocksRepository = stocksRepository;
        this.companyRepository = companyRepository;
    }



    public Optional<Stock> getSingleStockById(String Id ) {
        return stocksRepository.findById(Id);
    }

//    get stock by title
    public List<Company> searchCompany(String title ){
        return companyRepository.findByTickerOrCompanyName(title,title);


    }

    public Stock getStockData(String ticker) {
        List<Stock> stock = stocksRepository.findByTicker(ticker);
        return stock.get(0);
    }
}
