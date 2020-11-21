package com.updatestock.updatestock.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserForgotPasswordDto implements Serializable {
    private static final long serialVersionUID = 1L;
    
	@NotBlank(message = "email é obrigatório")
	@Email(message = "email inválido")
	@Size(max = 255, message = "email deve ser menor ou igual a que {max} caracteres")
	private String email;
}