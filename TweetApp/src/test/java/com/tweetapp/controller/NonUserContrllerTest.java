package com.tweetapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.tweetapp.config.JwtTokenUtil;
import com.tweetapp.dao.UserDao;
import com.tweetapp.entity.UserTable;
import com.tweetapp.exception.NotValidException;
import com.tweetapp.model.ForgotPassword;
import com.tweetapp.model.JwtRequest;
import com.tweetapp.model.RegisterRequest;
import com.tweetapp.repository.UserInfoRepostitory;

@SpringBootTest
public class NonUserContrllerTest {
	
	@InjectMocks
	NonUserController nonUserController;
	
	@Mock
	UserDao userDao;
	
	@Mock
	RegisterRequest registerRequest;
	
	@Mock
	private JwtTokenUtil jwtUtil;
	
	@Mock
	UserDetailsService userDetailService;
	
	@Mock
	AuthenticationManager authenticationManager;
	
	@Mock
	UserInfoRepostitory userInfoRepository;

	private String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyYW0iLCJleHAiOjE2Njk2NTcyMzMsImlhdCI6MTY2OTY1NTQzM30.1qRL1kphDcsfAkUfhaw4tQCUMZZRi2SadHOS7OfEOB5il7mDZ8gI_VoZgiXz3OzdtQI4ExSiw33huYjJaJvKnw";

	
	private RegisterRequest newUser() {
		registerRequest.setUserId("testing");
		registerRequest.setFirstName("john");
		registerRequest.setLastName("micky");
		registerRequest.setEmailId("john@xyz.com");
		registerRequest.setPassword("789456123");
		registerRequest.setConfirmPassword("789456123");
		registerRequest.setContactNumber("9876543210");
		return registerRequest;
	}
	
	private RegisterRequest newUserPasswordMismatch() {
		registerRequest.setUserId("testing");
		registerRequest.setFirstName("john");
		registerRequest.setLastName("micky");
		registerRequest.setEmailId("john@xyz.com");
		registerRequest.setPassword("789456123");
		registerRequest.setConfirmPassword("78945612");
		registerRequest.setContactNumber("9876543210");
		return registerRequest;
	}
	
	@Test
	public void userRegistrationTest() {
		when(userDao.registerUser(newUser())).thenReturn("User Registered Successfully");
		ResponseEntity<Object> actual = nonUserController.userRegistration(newUser());
		assertEquals("User Registered Successfully", actual.getBody());
	}
	
	@Test
	public void negativeUserRegistrationTest() {
		when(userDao.registerUser(newUserPasswordMismatch())).thenReturn("Please check your password");
		ResponseEntity<Object> actual = nonUserController.userRegistration(newUserPasswordMismatch());
		assertEquals("Please check your password", actual.getBody());
	}
	
	@Test
	public void forgotPassword() {
		when(userDao.forgotValidation(new ForgotPassword("user","ans"))).thenReturn(token);
		ResponseEntity<Object> actual = nonUserController.forgotPassword(new ForgotPassword("user","ans"));
		assertEquals(HttpStatus.OK, actual.getStatusCode());
	}
	
	@Test
	public void negativeForgotPassword() {
		when(userDao.forgotValidation(new ForgotPassword("user","ans"))).thenReturn(null);
		Exception exception = assertThrows(NotValidException.class, () -> { nonUserController.forgotPassword(null);});
		assertEquals("Not a Valid token",exception.getMessage());
	}
	
	
	



}
