package com.updatestock.updatestock.repository;

import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.updatestock.updatestock.model.PasswordReset;

public interface PasswordResetRepository extends PagingAndSortingRepository<PasswordReset, Integer> {
    public Optional<PasswordReset> findByToken(String token);
}