package com.updatestock.updatestock.repository;

import java.sql.Timestamp;
import com.updatestock.updatestock.dto.IMonthlyQtdProductOutputDto;
import com.updatestock.updatestock.model.ProductOutput;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductOutputRepository extends PagingAndSortingRepository<ProductOutput, Integer> {

    @Query("SELECT \n" +
                "COUNT(*) as qtdProductOutput, \n" +
                "TO_CHAR(DATE_TRUNC('month', tpo.updatedAt), 'MM/YYYY') as date \n" +
            "FROM ProductOutput as tpo \n" +
            "WHERE DATE_TRUNC('month', tpo.updatedAt) BETWEEN \n" +
                  "DATE_TRUNC('month', CURRENT_DATE) AND \n" +
                  "DATE_TRUNC('month', CAST(?1 as timestamp)) \n" +
            "GROUP BY \n" +
                "date"
        )
    public IMonthlyQtdProductOutputDto monthlyQtdProductOutput(Timestamp currentMonth);

}