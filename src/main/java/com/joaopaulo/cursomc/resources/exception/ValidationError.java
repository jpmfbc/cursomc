package com.joaopaulo.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<>();
	
	

	public ValidationError(Long timeStamp, Integer status, String erro, String message, String path) {
		super(timeStamp, status, erro, message, path);
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String message){
		errors.add(new FieldMessage(fieldName,message));
	}
	
}
