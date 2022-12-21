package com.tweetapp.dao;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.tweetapp.config.JwtTokenUtil;
import com.tweetapp.entity.UserTable;
import com.tweetapp.model.ForgotPassword;
import com.tweetapp.model.LoginData;
import com.tweetapp.model.RegisterRequest;
import com.tweetapp.repository.UserInfoRepostitory;

@Service
public class UserDaoImpl implements UserDao{
	
	@Autowired
	UserInfoRepostitory userInfoRepository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;
	
	@Autowired
	private LoginData loginData;
	
	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	@Override
	public String registerUser(RegisterRequest registerRequest) {
		
		if(registerRequest.getUserId().trim().isEmpty()) {
			return "Userid cannot be null or blank";
		}
		
		if(userInfoRepository.notInUserId(registerRequest.getUserId())!=null) {
			
			return "Userid already exist please try new combination";
		}
		if(userInfoRepository.notInEmailId(registerRequest.getEmailId())!=null) {
			return "Email already exsist";
		}
		if(!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
			return "Please check your Confirm password";
		}
		
		UserTable userTable = new UserTable();
		userTable.setUserId(registerRequest.getUserId());
		userTable.setFirstName(registerRequest.getFirstName());
		userTable.setLastName(registerRequest.getLastName());
		userTable.setContactNumber(registerRequest.getContactNumber());
		userTable.setPassword(registerRequest.getPassword());
		userTable.setEmailId(registerRequest.getEmailId());
		userTable.setSecurityQuestion(registerRequest.getSecurityQuestion());
		userInfoRepository.save(userTable);
		return "User Registered Successfully";
	}
	
	public boolean validateToken(String RawUserId,String RawToken){
		String userId = RawUserId.trim();
		String token = RawToken.trim();
		String jwtToken = token.substring(7,token.length());
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
		if(username!=null) {
			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
			
			try{
				if(jwtTokenUtil.validateToken(jwtToken, userDetails)&&username.equals(userId)) {
					logger.info("Valid token");
				return true;
				}
			}
			catch(Exception e) {
				return  false;
			}
		}
		return  false;
	}
	
	@Override
	public String reassignUserName(String userName) {
		if(userName.trim().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")) {
			return (userInfoRepository.getByEamilId(userName).getUserId());
		}
		return userName;
	}
	
	@Override
	public String updateLastLogOut(String userName) {
		if(userInfoRepository.notInUserId(userName.trim())!=null){
			 LocalDateTime now = LocalDateTime.now();  
			userInfoRepository.updateLogOut(now,userName);
		}
		return "Successfully Logged out!!!";
	}
	
	@Override
	public String updateLastLogingOut(String userName) {
		if(userInfoRepository.notInUserId(userName.trim())!=null){
			 LocalDateTime now = LocalDateTime.now();  
			userInfoRepository.updateLogIn(now,userName);
		}
		return "Updated!!!";
	}

	@Override
	public String forgotValidation(ForgotPassword forgotPassword) {
		UserTable userTable = userInfoRepository.getByUserId(forgotPassword.getUserName().trim());
		if(userTable.getUserId()!=null&&userTable.getSecurityQuestion().equals(forgotPassword.getSecurityQuestion1())){

			final UserDetails userDetails = jwtInMemoryUserDetailsService
					.loadUserByUsername(forgotPassword.getUserName());
			final String token = jwtTokenUtil.generateToken(userDetails);
			return token;
			
		}
		return "Not a valid Request";
	}

	@Override
	public LoginData getUserDetails(String token, String userId) {
		
		UserTable userTable = userInfoRepository.getByUserId(userId);
		loginData.setUserId(userId);
		loginData.setFirstName(userTable.getFirstName());
		loginData.setLastName(userTable.getLastName());
		loginData.setEmailId(userTable.getEmailId());
		loginData.setPhoneNo(userTable.getContactNumber());
		loginData.setJwtResponse(token);
		return loginData;
				
	}
	

}
