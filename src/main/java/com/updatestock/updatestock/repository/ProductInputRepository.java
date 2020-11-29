package com.updatestock.updatestock.repository;

import java.sql.Timestamp;
import com.updatestock.updatestock.dto.IMonthlyQtdProductInputDto;
import com.updatestock.updatestock.model.ProductInput;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductInputRepository extends PagingAndSortingRepository<ProductInput, Integer> {

    @Query("SELECT \n" +
                "SUM(tpi.id) as qtdProductInput, \n" +
                "TO_CHAR(DATE_TRUNC('month', tpi.updatedAt), 'MM/YYYY') as date \n" +
            "FROM ProductInput as tpi \n" +
            "WHERE DATE_TRUNC('month', tpi.updatedAt) BETWEEN \n" +
                  "DATE_TRUNC('month', CURRENT_DATE) AND \n" +
                  "DATE_TRUNC('month', CAST(?1 as timestamp)) \n" +
            "GROUP BY \n" +
                   "date"
          )
    public IMonthlyQtdProductInputDto monthlyQtdProductInput(Timestamp currentMonth);

}