package com.tweetapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.config.JwtTokenUtil;
import com.tweetapp.dao.JwtUserDetailsService;
import com.tweetapp.dao.UserDao;
import com.tweetapp.entity.UserTable;
import com.tweetapp.exception.NotValidException;
import com.tweetapp.model.ForgotPassword;
import com.tweetapp.model.JwtRequest;
import com.tweetapp.model.JwtResponse;
import com.tweetapp.model.LoginData;
import com.tweetapp.model.RegisterRequest;
import com.tweetapp.model.ValidationData;
import com.tweetapp.repository.UserInfoRepostitory;

import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/api/v1.0/tweets/")
public class NonUserController {

	@Autowired
	UserDao userDao;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;
	
	@Autowired
	private LoginData loginData;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private UserInfoRepostitory userInfoRepository;

	private static final Logger logger = LoggerFactory.getLogger(NonUserController.class);

	@PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)throws  Exception {
		
	    authenticationRequest.setUsername(userDao.reassignUserName(authenticationRequest.getUsername()));
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		if(token!=null) {
			loginData = userDao.getUserDetails(token,userDetails.getUsername());
			userDao.updateLastLogingOut(authenticationRequest.getUsername());
		}
		logger.info("Token : "+token);

		return new ResponseEntity<>(loginData,HttpStatus.OK);
	}

	@PostMapping(value="/registers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> userRegistration(@Validated @RequestBody(required = true) RegisterRequest registerRequest) {
		
		return new ResponseEntity<>(new ValidationData(userDao.registerUser(registerRequest)), HttpStatus.OK);

	}
	
	@PostMapping(value="/forgotPassword", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> forgotPassword(@Validated @RequestBody ForgotPassword forgotPassword){
		
		if(forgotPassword!=null) {
			return new ResponseEntity<>(new JwtResponse(userDao.forgotValidation(forgotPassword)),HttpStatus.OK);
		}
		
		throw new NotValidException("Not a Valid token");
		
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
