package com.tweetapp.model;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;

@Component
public class ForgotPassword {
	
	@NotBlank(message="userName cannot be blank")
	private String userName;
	@NotBlank(message="securityQuestions cannot be blank")
	private String securityQuestion1;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSecurityQuestion1() {
		return securityQuestion1;
	}
	public void setSecurityQuestion1(String securityQuestion1) {
		this.securityQuestion1 = securityQuestion1;
	}
	@Override
	public String toString() {
		return "ForgotPassword [userName=" + userName + ", securityQuestion1=" + securityQuestion1 + "]";
	}
	public ForgotPassword(@NotBlank(message = "userName cannot be blank") String userName,
			@NotBlank(message = "securityQuestions cannot be blank") String securityQuestion1) {
		super();
		this.userName = userName;
		this.securityQuestion1 = securityQuestion1;
	}
	public ForgotPassword() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
