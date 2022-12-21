package com.tweetapp.model;

import org.springframework.stereotype.Component;

@Component
public class ValidationData {
	
	private String validationMessage;

	public String getValidationMessage() {
		return validationMessage;
	}

	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}

	@Override
	public String toString() {
		return "ValidationData [validationMessage=" + validationMessage + "]";
	}

	public ValidationData(String validationMessage) {
		super();
		this.validationMessage = validationMessage;
	}

	public ValidationData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
