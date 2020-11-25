package com.updatestock.updatestock.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserRolesDto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer userId;
    private Integer roleId;
    private String roleName;

    public UserRolesDto(Integer userId, Integer roleId, String roleName) {
        this.userId = userId;
        this.roleId = roleId;
        this.roleName = roleName;
    }
    
}
