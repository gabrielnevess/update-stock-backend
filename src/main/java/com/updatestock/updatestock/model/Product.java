package com.updatestock.updatestock.model;

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
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "measurement_unit_id")
	private MeasurementUnit measurementUnit;
	
	@NotBlank(message = "nome do produto é obrigatório")
	@Size(min = 5, max = 255, message = "nome do produto deve ser igual ou superior a {min} caracteres e menor que {max} caracteres")
	@Column(name = "name")
	private String name;

	@NotBlank(message = "modelo é obrigatório")
	@Size(min = 5, max = 255, message = "modelo deve ser igual ou superior a {min} caracteres e menor que {max} caracteres")
	@Column(name = "model")
	private String model;

	@NotBlank(message = "serial é obrigatório")
	@Size(min = 5, max = 255, message = "serial deve ser igual ou superior a {min} caracteres e menor que {max} caracteres")
	@Column(name = "serial")
	private String serial;
		
	@CreationTimestamp
	@Column(name = "created_at")
	private Timestamp createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

}
