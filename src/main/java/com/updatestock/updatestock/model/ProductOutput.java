package com.updatestock.updatestock.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tb_product_output")
@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class ProductOutput {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "product_id", nullable = false)
    private Integer productId;

	@NotNull(message = "quantidade para entrada é obrigatório")
	@Column(name = "qtd")
    private Integer qtd;
	
	@NotBlank(message = "observação é obrigatório")
	@Size(min = 10, max = 500, message = "observação deve ser igual ou superior a {min} caracteres e menor que {max} caracteres")
	@Column(name = "observation")
	private String observation;

	@CreationTimestamp
	@Column(name = "created_at")
	private Timestamp createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

}
