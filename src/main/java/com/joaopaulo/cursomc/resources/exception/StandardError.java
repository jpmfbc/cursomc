package com.joaopaulo.cursomc.resources.exception;

import java.io.Serializable;

public class StandardError implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private Long TimeStamp;
	private Integer status;
	private String erro;
	private String message;
	private String path;
		
	public StandardError(Long timeStamp, Integer status, String erro, String message, String path) {
		super();
		TimeStamp = timeStamp;
		this.status = status;
		this.erro = erro;
		this.message = message;
		this.path = path;
	}
	
	public Long getTimeStamp() {
		return TimeStamp;
	}
	public void setTimeStamp(Long timeStamp) {
		TimeStamp = timeStamp;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getErro() {
		return erro;
	}
	public void setErro(String erro) {
		this.erro = erro;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
