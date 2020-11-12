package com.updatestock.updatestock.repository;

import com.updatestock.updatestock.model.ProductInput;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductInputRepository extends PagingAndSortingRepository<ProductInput, Integer> {
}