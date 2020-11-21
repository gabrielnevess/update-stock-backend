package com.updatestock.updatestock.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.Optional;
import com.updatestock.updatestock.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    public Optional<User> findByEmail(String email);
    public Optional<User> findByLogin(String login);
}
