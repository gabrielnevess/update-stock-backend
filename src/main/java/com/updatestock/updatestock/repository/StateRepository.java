package com.updatestock.updatestock.repository;

import com.updatestock.updatestock.model.State;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StateRepository extends PagingAndSortingRepository<State, Integer> {
}