package com.updatestock.updatestock.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_user")
@Data
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@OneToOne(targetEntity = Role.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	private Role role;

	@NotBlank(message = "login é obrigatório")
	@Pattern(regexp = "^(?!.*([A-Za-z0-9._@])\\1{2})(?=.*[a-z])(?=.*\\d)[A-Za-z0-9._@]+$", message = "login dever conter só letras, números . _ e @ | login dever conter no mínimo 1 caracter letra e número e no máximo 2 caracteres iguais")
	@Size(min = 5, max = 20, message = "login do usuário deve ter no mínimo {min} caracteres e no máximo {max} caracteres")
	@Column(name = "login")
	private String login;

	@NotBlank(message = "senha é obrigatório")
	@Size(min = 5, max = 255, message = "senha deve ser igual ou superior a {min} caracteres e menor que {max} caracteres")
	@Column(name = "password")
	private String password;

	@NotBlank(message = "nome do usuário é obrigatório")
	@Size(min = 5, max = 255, message = "nome do usuário deve ter no mínimo {min} caracteres e no máximo {max} caracteres")
	@Column(name = "name")
	private String name;

	@Column(name = "token")
	private String token;

	@Column(name = "active")
	private Boolean active = true;
    
    @CreationTimestamp
	@Column(name = "created_at")
	private Timestamp createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

}