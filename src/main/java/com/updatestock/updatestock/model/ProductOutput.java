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

import io.swagger.annotations.ApiModelProperty;
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
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("Código")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ApiModelProperty("Produto")
	@OneToOne(targetEntity = Product.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private Product product;

	@ApiModelProperty("Usuário")
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@ApiModelProperty("Quantidade")
	@NotNull(message = "quantidade para saída é obrigatório")
	@NumberPositiveAndNotZero
	@Column(name = "qtd")
    private Integer qtd;
	
	@ApiModelProperty("Observação")
	@NotBlank(message = "observação é obrigatório")
	@Size(min = 10, max = 500, message = "observação deve ser igual ou superior a {min} caracteres e menor que {max} caracteres")
	@Column(name = "observation")
	private String observation;

	@ApiModelProperty(value = "Data de Criação", hidden = true)
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private Timestamp createdAt;

	@ApiModelProperty(value = "Data de Atualização", hidden = true)
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

}
