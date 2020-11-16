package com.updatestock.updatestock.repository;

import java.util.Optional;

import com.updatestock.updatestock.model.Stock;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StockRepository extends PagingAndSortingRepository<Stock, Integer> {
    public Optional<Stock> findByProduct_Id(Integer productId);
}