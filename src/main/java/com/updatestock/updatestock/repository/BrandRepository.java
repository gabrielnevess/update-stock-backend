package com.updatestock.updatestock.repository;

import java.util.List;
import com.updatestock.updatestock.model.Brand;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BrandRepository extends PagingAndSortingRepository<Brand, Integer> {

    @Query("SELECT b FROM Brand as b")
    public List<Brand> findBrandAll();

}
