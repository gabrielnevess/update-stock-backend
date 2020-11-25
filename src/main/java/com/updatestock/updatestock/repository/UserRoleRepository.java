package com.updatestock.updatestock.repository;

import java.util.List;

import com.updatestock.updatestock.dto.UserRolesDto;
import com.updatestock.updatestock.model.UserRole;
import com.updatestock.updatestock.model.UserRoleId;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRoleRepository extends PagingAndSortingRepository<UserRole, UserRoleId> {

    @Query("SELECT DISTINCT \n" +
                "NEW com.updatestock.updatestock.dto.UserRolesDto(" + 
                    "ur.userRoleId.userId, " + 
                    "r.id, " + 
                    "r.name" +
                ") \n" + 
            "FROM Role as r, \n" +
                 "UserRole as ur \n" + 
            "WHERE ur.userRoleId.userId = ?1 AND \n" +  
                  "r.id NOT IN ("+ 
                    "SELECT \n" + 
                        "ur.userRoleId.roleId \n" + 
                    "FROM Role as r \n" + 
                    "INNER JOIN UserRole as ur \n" + 
                    "ON ur.userRoleId.roleId = r.id AND \n" + 
                    "ur.userRoleId.userId = ?1 \n" + 
                ")"
    )
    public List<UserRolesDto> findByListLeft(Integer userId);

    @Query("SELECT \n" +
                "NEW com.updatestock.updatestock.dto.UserRolesDto(" +
                    "ur.userRoleId.userId, " +
                    "ur.userRoleId.roleId, " +
                    "r.name as roleName" +
                ") \n" +
            "FROM Role as r \n" +
            "INNER JOIN UserRole as ur \n" +
            "ON ur.userRoleId.roleId = r.id AND \n" +
               "ur.userRoleId.userId = ?1"
    )
    public List<UserRolesDto> findByListRight(Integer userId);
}