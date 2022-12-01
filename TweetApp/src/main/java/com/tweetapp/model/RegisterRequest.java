package com.tweetapp.model;


import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.stereotype.Component;

@Component
public class RegisterRequest {
	
	private String userId;
	@NotBlank(message="First Name cannot be blank")
	private String firstName;
	@NotBlank(message="Last Name cannot be blank")
	private String lastName;
	@NotBlank(message="Password cannot be blank")
	private String password;
	@NotBlank(message="Confirm Password cannot be blank")
	private String confirmPassword;
	@NotBlank(message="EmailId cannot be blank")
	@Email(message="Please give the valid email id")
	private String emailId;
	@NotBlank(message="Contact Number cannot be blank")
	@Pattern(regexp = "^((\\\\+91-?)|0)?[0-9]{10}$")
	@Column(length = 10)
	private String contactNumber;
	@NotBlank(message="Security qusetion cannot be blank")
	private String securityQuestion;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	@Override
	public String toString() {
		return "RegisterRequest [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", password=" + password + ", confirmPassword=" + confirmPassword + ", emailId=" + emailId
				+ ", contactNumber=" + contactNumber + ", securityQuestion=" + securityQuestion + "]";
	}
	public RegisterRequest(String userId, @NotBlank(message = "First Name cannot be blank") String firstName,
			@NotBlank(message = "Last Name cannot be blank") String lastName,
			@NotBlank(message = "Password cannot be blank") String password,
			@NotBlank(message = "Confirm Password cannot be blank") String confirmPassword,
			@NotBlank(message = "EmailId cannot be blank") @Email(message = "Please give the valid email id") String emailId,
			@NotBlank(message = "Contact Number cannot be blank") @Pattern(regexp = "^((\\\\+91-?)|0)?[0-9]{10}$") String contactNumber,
			@NotBlank(message = "Security qusetion cannot be blank") String securityQuestion) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.emailId = emailId;
		this.contactNumber = contactNumber;
		this.securityQuestion = securityQuestion;
	}
	public RegisterRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
