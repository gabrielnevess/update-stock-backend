package com.updatestock.updatestock.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
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
    
    @ApiModelProperty("Código do Usuário")
    private Integer userId;
    
    @ApiModelProperty("Código da Permissão")
    private Integer roleId;
    
    @ApiModelProperty("Nome da Permissão")
    private String roleName;

    public UserRolesDto(Integer userId, Integer roleId, String roleName) {
        this.userId = userId;
        this.roleId = roleId;
        this.roleName = roleName;
    }
    
}
