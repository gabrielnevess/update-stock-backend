package com.updatestock.updatestock.repository;

import com.updatestock.updatestock.model.Stock;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StockRepository extends PagingAndSortingRepository<Stock, Integer> {
}