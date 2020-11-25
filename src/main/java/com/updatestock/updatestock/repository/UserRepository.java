package com.updatestock.updatestock.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.Optional;
import com.updatestock.updatestock.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    public Optional<User> findByEmail(String email);
    public Optional<User> findByLogin(String login);

    @Query(
           value = "SELECT u FROM User as u WHERE u.id <> ?1",
           countQuery = "SELECT COUNT(*) FROM User as u WHERE u.id <> ?1"
        )
    public Page<User> findAllByNotUser_Id(Integer userId, Pageable pageable);
}
