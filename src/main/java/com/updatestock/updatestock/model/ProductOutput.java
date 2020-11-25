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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.updatestock.updatestock.validator.NumberPositiveAndNotZero;

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
public class ProductOutput implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@OneToOne(targetEntity = Product.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private Product product;

	@NotNull(message = "quantidade para saída é obrigatório")
	@NumberPositiveAndNotZero
	@Column(name = "qtd")
    private Integer qtd;
	
	@NotBlank(message = "observação é obrigatório")
	@Size(min = 10, max = 500, message = "observação deve ser igual ou superior a {min} caracteres e menor que {max} caracteres")
	@Column(name = "observation")
	private String observation;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private Timestamp createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

}
