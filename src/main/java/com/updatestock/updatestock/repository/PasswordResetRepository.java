package com.updatestock.updatestock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.updatestock.updatestock.model.PasswordReset;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {
    public Optional<PasswordReset> findByToken(String token);
}
