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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tb_brand")
@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Brand implements Serializable {
    
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
		
	@NotBlank(message = "nome da marca é obrigatório")
	@Size(min = 3, max = 50, message = "nome da marca deve ser igual ou superior a {min} caracteres e menor que {max} caracteres")
	@Column(name = "name")
	private String name;

	@CreationTimestamp
	@Column(name = "created_at")
	private Timestamp createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

}
