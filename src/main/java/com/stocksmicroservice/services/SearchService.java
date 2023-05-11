package com.stocksmicroservice.services;


import com.stocksmicroservice.collections.Stock;
import com.stocksmicroservice.repositories.StocksRepository;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearchService {

    private final StocksRepository stocksRepository;

    public SearchService(StocksRepository stocksRepository) {
        this.stocksRepository = stocksRepository;
    }



    public Optional<Stock> getSingleStockById(String Id ) {
        return stocksRepository.findById(Id);
    }


//    public List<Stock> getFilteredStocksByName(String searchTerm){
//
//        // Construct the query
//        Document query = new Document();
//        query.append("title", new Document("$regex", searchTerm).append("$options", "i"));
//
//
//
//        return stocksRepository.findBy(query);
//
//
//
////        Predicate predicate = Stocks.title.equalsIgnoreCase("dave");
////
////        stocksRepository.findAll(predicate);
//
//        // Predicate predicate = Stock.stock.startsWithIgnoreCase(message);
//        // .and(user.lastname.startsWithIgnoreCase("mathews"));
//
//
//        //Document query = new Document();
//        // query.append("name", new Document("$regex", searchTerm).append("$options", "i"));
//
//        //return stocksRepository.findAll({"stock": message});
//        // return stocksRepository.findAll(predicate);
//
//    }

}
