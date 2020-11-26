package com.updatestock.updatestock.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "tb_product")
@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("Código")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ApiModelProperty("Marca")
	@OneToOne(targetEntity = Brand.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@ApiModelProperty("Unidade de Medida")
	@OneToOne(targetEntity = MeasurementUnit.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "measurement_unit_id")
	private MeasurementUnit measurementUnit;
	
	@ApiModelProperty("Nome")
	@NotBlank(message = "nome do produto é obrigatório")
	@Size(min = 5, max = 255, message = "nome do produto deve ser igual ou superior a {min} caracteres e menor que {max} caracteres")
	@Column(name = "name")
	private String name;

	@ApiModelProperty("Modelo")
	@NotBlank(message = "modelo é obrigatório")
	@Size(min = 5, max = 255, message = "modelo deve ser igual ou superior a {min} caracteres e menor que {max} caracteres")
	@Column(name = "model")
	private String model;

	@ApiModelProperty("Serial")
	@NotBlank(message = "serial é obrigatório")
	@Size(min = 5, max = 255, message = "serial deve ser igual ou superior a {min} caracteres e menor que {max} caracteres")
	@Column(name = "serial")
	private String serial;
		
	@ApiModelProperty(value = "Data de Criação", hidden = true)
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private Timestamp createdAt;

	@ApiModelProperty(value = "Data de Atualização", hidden = true)
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

}
