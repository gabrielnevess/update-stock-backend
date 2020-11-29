package com.updatestock.updatestock.repository;

import java.util.List;
import com.updatestock.updatestock.model.MeasurementUnit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MeasurementUnitRepository extends PagingAndSortingRepository<MeasurementUnit, Integer> {

    @Query("SELECT mu FROM MeasurementUnit as mu")
    public List<MeasurementUnit> findMeasurementUnitAll();

}