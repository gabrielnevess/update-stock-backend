package com.updatestock.updatestock.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Embeddable
@Data
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class UserRoleId implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("Código do Usuário")
    @Column(name = "user_id")
    private Integer userId;

    @ApiModelProperty("Código da Permissão")
    @Column(name = "role_id")
    private Integer roleId;

}