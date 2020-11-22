package com.updatestock.updatestock.repository;

import com.updatestock.updatestock.model.UserRole;
import com.updatestock.updatestock.model.UserRoleId;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRoleRepository extends PagingAndSortingRepository<UserRole, UserRoleId> {
}