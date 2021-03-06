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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "tb_password_reset")
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class PasswordReset implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int EXPIRATION = 60 * 24;

    @ApiModelProperty("Código")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ApiModelProperty("Token")
    @NotBlank(message = "token é obrigatório")
    @Size(max = 255, message = "token deve ter no máximo {max} caracteres")
    @Column(name = "token")
    private String token;

    @ApiModelProperty("Usuário")
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
    private User user;
    
    @ApiModelProperty("Token Utilizado True/False")
	@NotNull(message = "Status token utilizado é obrigatório")
	@Column(name = "used_token")
	private Boolean usedToken = false;

    @ApiModelProperty("Data de Expiração")
    @Column(name = "date_expiration")
    private Date expiryDate;

	@ApiModelProperty(value = "Data de Criação", hidden = true)
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private Timestamp createdAt;

	@ApiModelProperty(value = "Data de Atualização", hidden = true)
	@UpdateTimestamp
	@Column(name = "updated_at")
	private Timestamp updatedAt;

    public PasswordReset(String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

}