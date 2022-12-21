package com.tweetapp.model;

import org.springframework.stereotype.Component;

@Component
public class LoginData {
	
	private String jwtResponse;
	private String userId;
	private String firstName;
	private String lastName;
	private String emailId;
	private String phoneNo;
	
	public String getJwtResponse() {
		return jwtResponse;
	}
	public void setJwtResponse(String jwtResponse) {
		this.jwtResponse = jwtResponse;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	@Override
	public String toString() {
		return "LoginData [jwtResponse=" + jwtResponse + ", userId=" + userId + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", emailId=" + emailId + ", phoneNo=" + phoneNo + "]";
	}
	public LoginData(String jwtResponse, String userId, String firstName, String lastName, String emailId,
			String phoneNo) {
		super();
		this.jwtResponse = jwtResponse;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.phoneNo = phoneNo;
	}
	public LoginData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
