package com.updatestock.updatestock.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.updatestock.updatestock.validator.ValidateUniqueUser;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "tb_user")
@Data
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@ValidateUniqueUser(
	fieldId = "id",
	fieldEmail = "email",
	fieldLogin = "login"
)
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("Código")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ApiModelProperty("E-mail")
	@NotBlank(message = "e-mail é obrigatório")
	@Email(message = "e-mail inválido")
	@Size(max = 255, message = "e-mail deve ser menor ou igual a que {max} caracteres")
	@Column(name = "email")
	private String email;

	@ApiModelProperty("Login")
	@NotBlank(message = "login é obrigatório")
	@Pattern(regexp = "^(?!.*([A-Za-z0-9._@])\\1{2})(?=.*[a-z])(?=.*\\d)[A-Za-z0-9._@]+$", message = "login dever conter só letras, números . _ e @ | login dever conter no mínimo 1 caracter letra e número e no máximo 2 caracteres iguais")
	@Size(min = 5, max = 20, message = "login do usuário deve ter no mínimo {min} caracteres e no máximo {max} caracteres")
	@Column(name = "login")
	private String login;

	@ApiModelProperty("Senha")
	@NotBlank(message = "senha é obrigatório")
	@Size(min = 5, max = 255, message = "senha deve ser igual ou superior a {min} caracteres e menor que {max} caracteres")
	@Column(name = "password")
	private String password;

	@ApiModelProperty("Nome")
	@NotBlank(message = "nome do usuário é obrigatório")
	@Size(min = 5, max = 255, message = "nome do usuário deve ter no mínimo {min} caracteres e no máximo {max} caracteres")
	@Column(name = "name")
	private String name;

	@ApiModelProperty("Token JWT")
	@Column(name = "token")
	private String token;

	@ApiModelProperty("Usuário Ativo True/False")
	@Column(name = "active")
	private Boolean active = true;

	@ApiModelProperty(value = "Data de Criação", hidden = true)
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private Timestamp createdAt;

	@ApiModelProperty(value = "Data de Atualização", hidden = true)
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tb_user_role", 
			   joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), 
			   inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<Role> roles;

}