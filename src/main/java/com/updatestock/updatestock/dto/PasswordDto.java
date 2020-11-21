package com.updatestock.updatestock.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class PasswordDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "token é obrigatório")
    private String token;

    @NotBlank(message = "senha é obrigatório")
    @Size(min = 5, max = 255, message = "senha deve ser igual ou superior a {min} caracteres e menor que {max} caracteres")
    private String newPassword;

}