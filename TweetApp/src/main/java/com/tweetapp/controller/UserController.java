package com.tweetapp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.dao.TweetDao;
import com.tweetapp.dao.TweetDaoImpl;
import com.tweetapp.dao.UserDaoImpl;
import com.tweetapp.entity.ReplyTweetTable;
import com.tweetapp.entity.TweetTable;
import com.tweetapp.exception.NotValidException;
import com.tweetapp.model.JwtResponse;
import com.tweetapp.model.ValidationData;
import com.tweetapp.repository.ReplyTweetInfoRepository;
import com.tweetapp.repository.TweetInfoRepository;

import io.swagger.v3.oas.annotations.media.Content;



@RestController
@CrossOrigin
@RequestMapping("/api/v1.0/tweets/")
public class UserController {

	@Autowired
	TweetInfoRepository tweetInfoRepository;

	@Autowired
	ReplyTweetInfoRepository replyTweetInfoRepository;

	@Autowired
	UserDaoImpl userDaoImpl;

	@Autowired
	TweetDao tweetDao;
	
	

	@PostMapping("/addTweet")
	public ResponseEntity<Object> postNewTweet(@RequestHeader(name = "Authorization", required = true) String token,
			 @Valid @RequestBody(required = true) TweetTable tweetData) {

		if (token != null && userDaoImpl.validateToken(tweetData.getUserId(), token)) {
			
			return new ResponseEntity<>(new JwtResponse(tweetDao.postTweet(tweetData)), HttpStatus.OK);
		}
		throw new NotValidException("Not a Valid token");

	}


	@PostMapping("/replyTweet")
	public ResponseEntity<Object> replyTweet(@RequestHeader(name = "Authorization", required = true) String token,
			@Valid @RequestBody ReplyTweetTable replyTweetTable) {
		if (token != null && userDaoImpl.validateToken(replyTweetTable.getUserId(), token)) {
			
			return new ResponseEntity<>(new ValidationData(tweetDao.replyTweet(replyTweetTable)), HttpStatus.OK);
		}
		throw new NotValidException("Not a Valid token");
	}
	
	@GetMapping("/getReplyTweet/{tweetId}")
	public ResponseEntity<List<ReplyTweetTable>> getReplyTweetById(@RequestHeader(name = "Authorization", required = true) String token,
			@PathVariable Long tweetId){
		return new ResponseEntity<>(tweetDao.getRelyTweet(tweetId),HttpStatus.OK);
	}

	@GetMapping(path = "/{username}/getUserTweet", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getUserTweet(@RequestHeader(name = "Authorization", required = true) String token,
			@PathVariable(required = true) String username) {
		if (username != null) {
			if (token != null) {
				ArrayList<TweetTable> tweetData = tweetDao.getUserTweets(username);
				return new ResponseEntity<>(tweetData, HttpStatus.OK);
			}
		}
		throw new NotValidException("Not a Valid token");
	}

	
	@GetMapping(path = "/getAllTweet",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getALLTweet(@RequestHeader(value = "Authorization", required = true) String token,
			@RequestParam(required = true) String username) {
		System.out.println(token);
		if (username != null) {
			if (token != null && userDaoImpl.validateToken(username, token)) {
				ArrayList<TweetTable> allTweetData = tweetDao.getAllTweets();
				return new ResponseEntity<>(allTweetData, HttpStatus.OK);
			}
		}
		throw new NotValidException("Not a Valid token");
	}

	@PatchMapping(path = "/updateTweet", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updateTweet(@RequestHeader(name = "Authorization", required = true) String token,
			@Valid @RequestBody(required = true) TweetTable tweetTable) {

		if (tweetTable != null && token != null && userDaoImpl.validateToken(tweetTable.getUserId(), token)) {
			return new ResponseEntity<>(new ValidationData(tweetDao.updateByTweeId(tweetTable)), HttpStatus.OK);
		}
		throw new NotValidException("Not a Valid token");
	}

	@DeleteMapping(path = "/deleteTweet", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteTweet(@RequestHeader(name = "Authorization", required = true) String token,
			@RequestParam(required = true) String userId, @RequestParam(required = true) Long tweetId) {
		
		if (userId != null && tweetId != null && token != null && userDaoImpl.validateToken(userId, token)) {
			
			return new ResponseEntity<>(new ValidationData(tweetDao.deleteTweet(userId, tweetId)), HttpStatus.OK);
		}
		throw new NotValidException("Not a Valid token");
	}

	@PatchMapping(path = "/{userId}/like/{tweetId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> likeUpdate(@RequestHeader(name = "Authorization", required = true) String token,
			@PathVariable String userId, @PathVariable Long tweetId) {
		if(token != null && userDaoImpl.validateToken(userId, token)) {
		return new ResponseEntity<>(new ValidationData(tweetDao.likeTweet(userId, tweetId)), HttpStatus.OK);
		}
		throw new NotValidException("Not a Valid token");
	}

	@GetMapping(path = "/viewAllUsers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllUsers(@RequestHeader(name = "Authorization", required = true) String token,@RequestParam String userId) {
		
		if(token != null && userDaoImpl.validateToken(userId, token)) {
			return new ResponseEntity<>(tweetDao.viewAllUser(), HttpStatus.OK);
		}
		throw new NotValidException("Not a Valid token");
	}
	
	@GetMapping(path = "/search/userId/{search}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> searchUserId(@RequestHeader(name = "Authorization", required = true) String token,@PathVariable String search,@RequestParam String userId){
		if(token != null && userDaoImpl.validateToken(userId, token)) {
			return new ResponseEntity<>(tweetDao.searchId(search), HttpStatus.OK);
		}
		throw new NotValidException("Not a Valid token");
	}

	@PatchMapping(path = "/resetPassword/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> resetUserPassword(@RequestHeader(name = "Authorization",  required = true) String token,@PathVariable String userId,@RequestBody String newPassword) {
		System.out.println(token);
		if(userId != null&&newPassword!=null&&token != null && userDaoImpl.validateToken(userId, token)) {
			return new ResponseEntity<>(new ValidationData(tweetDao.resetPassword(userId, newPassword)),HttpStatus.OK);
	}
		throw new NotValidException("Not a Valid token");
	}

	@PatchMapping(path = "/logout/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> logOutUser(@RequestHeader(name = "Authorization", required = true) String token,@PathVariable String userName) {
		if(userName!=null&& userDaoImpl.validateToken(userName, token)) {
			return new ResponseEntity<>(new ValidationData(userDaoImpl.updateLastLogOut(userName)), HttpStatus.OK);
		}
		throw new NotValidException("Not a Valid token");
	}

}
