package com.tweetapp.entity;

import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="USER_INFO")
public class UserTable {
	
	@Id
	@Column(nullable = false)
	private String userId;
	@NotBlank(message="First Name cannot be blank")
	private String firstName;
	@NotBlank(message="Last Name cannot be blank")
	private String lastName;
	@NotBlank(message="Password cannot be blank")
	private String password;
	@NotBlank(message="EmailId cannot be blank")
	@Email(message="Please give the valid email id")
	private String emailId;
	@NotBlank(message="Contact Number cannot be blank")
	@Pattern(regexp = "^((\\\\+91-?)|0)?[0-9]{10}$")
	@Column(length = 10)
	private String contactNumber;
	@NotBlank(message="Security Qustion cannot be blank")
	private String securityQuestion;
	@Column(name = "creation_date", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date date;
	private Date LastLoginDate;
	private Date LastLogoutDate;
	
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getLastLoginDate() {
		return LastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		LastLoginDate = lastLoginDate;
	}
	public Date getLastLogoutDate() {
		return LastLogoutDate;
	}
	public void setLastLogoutDate(Date lastLogoutDate) {
		LastLogoutDate = lastLogoutDate;
	}
	@Override
	public String toString() {
		return "UserTable [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", password="
				+ password + ", emailId=" + emailId + ", contactNumber=" + contactNumber + ", securityQuestion="
				+ securityQuestion + ", date=" + date + ", LastLoginDate=" + LastLoginDate + ", LastLogoutDate="
				+ LastLogoutDate + "]";
	}
	public UserTable(String userId, @NotBlank(message = "First Name cannot be blank") String firstName,
			@NotBlank(message = "Last Name cannot be blank") String lastName,
			@NotBlank(message = "Password cannot be blank") String password,
			@NotBlank(message = "EmailId cannot be blank") @Email(message = "Please give the valid email id") String emailId,
			@NotBlank(message = "Contact Number cannot be blank") @Pattern(regexp = "^((\\\\+91-?)|0)?[0-9]{10}$") String contactNumber,
			String securityQuestion, Date date, Date lastLoginDate, Date lastLogoutDate) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.emailId = emailId;
		this.contactNumber = contactNumber;
		this.securityQuestion = securityQuestion;
		this.date = date;
		LastLoginDate = lastLoginDate;
		LastLogoutDate = lastLogoutDate;
	}
	public UserTable() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
