package com.tweetapp.dao;

import org.springframework.stereotype.Service;

import com.tweetapp.entity.UserTable;
import com.tweetapp.model.ForgotPassword;
import com.tweetapp.model.RegisterRequest;

@Service
public interface UserDao {
	
	public String registerUser(RegisterRequest registerRequest);
	
	public String reassignUserName(String userName);
	
	public String updateLastLogOut(String userName);
	
	public String updateLastLogingOut(String userName);
	
	public String forgotValidation(ForgotPassword forgotPassword);

}
