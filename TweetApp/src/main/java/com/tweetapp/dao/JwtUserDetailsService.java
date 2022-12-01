package com.tweetapp.dao;

import org.springframework.stereotype.Service;

import com.tweetapp.entity.UserTable;
import com.tweetapp.repository.UserInfoRepostitory;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserInfoRepostitory userInfoRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserTable userInformation = userInfoRepository.getByUserId(username);
		BCryptPasswordEncoder bcrypto = new BCryptPasswordEncoder();
		
		if (username!=null) {
			return new User(userInformation.getUserId(), bcrypto.encode(userInformation.getPassword()) ,new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}

	}

}
