package com.joaopaulo.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class EmailDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	@NotEmpty(message="Preenchimento obrigatório")
	@Email(message="Email invalido")
	private String email;
	
	public EmailDTO() {
		
	}
	
	

	public EmailDTO(@NotEmpty(message = "Preenchimento obrigatório") @Email(message = "Email invalido") String email) {
		super();
		this.email = email;
	}



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
