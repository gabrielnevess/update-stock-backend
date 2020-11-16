package com.updatestock.updatestock.repository;

import java.util.Optional;

import com.updatestock.updatestock.model.Stock;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StockRepository extends PagingAndSortingRepository<Stock, Integer> {
    @Query("SELECT SUM(s.qtd) FROM Stock s WHERE s.product.id = ?1")
    public Optional<Integer> sumStockByIdProduct(Integer productId);
}