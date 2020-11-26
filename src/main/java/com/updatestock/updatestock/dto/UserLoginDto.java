package com.updatestock.updatestock.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserLoginDto {
    @ApiModelProperty("Login")
    private String login;
    @ApiModelProperty("Senha")
    private String password;
}