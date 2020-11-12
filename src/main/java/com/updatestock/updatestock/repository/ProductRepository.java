package com.updatestock.updatestock.repository;

import com.updatestock.updatestock.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
}
