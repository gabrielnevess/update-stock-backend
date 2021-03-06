package com.updatestock.updatestock.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.sql.Timestamp;


@Entity
@Table(name = "tb_role")
@Data
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty("Código")
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Integer id;
	
	@ApiModelProperty("Nome")
    @NotBlank(message = "nome da permissão é obrigatório")
	@Size(min = 5, max = 50, message = "nome da permissão deve ter no mínimo {min} caracteres e no máximo {max} caracteres")
	@Column(name = "name")
	private String name;

	@ApiModelProperty(value = "Data de Criação", hidden = true)
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private Timestamp createdAt;

	@ApiModelProperty(value = "Data de Atualização", hidden = true)
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;
}
