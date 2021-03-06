package com.updatestock.updatestock.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tb_measurement_unit")
@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class MeasurementUnit implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("Código")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ApiModelProperty("Nome")
	@NotBlank(message = "nome da unidade de medida é obrigatório")
	@Size(min = 3, max = 50, message = "nome da unidade de medida deve ser igual ou superior a {min} caracteres e menor que {max} caracteres")
	@Column(name = "name")
	private String name;

	@ApiModelProperty("Prefixo")
	@NotBlank(message = "prefixo é obrigatório")
	@Size(min = 1, max = 15, message = "prefixo deve ser igual ou superior a {min} caracteres e menor que {max} caracteres")
	@Column(name = "prefix")
	private String prefix;

	@ApiModelProperty(value = "Data de Criação", hidden = true)
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private Timestamp createdAt;

	@ApiModelProperty(value = "Data de Atualização", hidden = true)
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;
    
}