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
public class TokenDto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty("Token")
    private String token;
}