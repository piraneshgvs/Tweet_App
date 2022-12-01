package com.tweetapp.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.tweetapp.entity.ReplyTweetTable;
import com.tweetapp.entity.TweetTable;
import com.tweetapp.repository.ReplyTweetInfoRepository;


@Service
public interface TweetDao {
	
	public ArrayList<TweetTable> getUserTweets(String userId);
	
	public ArrayList<TweetTable> getAllTweets();
	
	public String updateByTweeId(TweetTable tweetTable);
	
	public String deleteTweet(String userId, Long tweetId);
	
	public String likeTweet(String userId, Long tweetId);
	
	public String postTweet(TweetTable tweetData);
	
	public String replyTweet(ReplyTweetTable replyTweetTable);
	
	public List<String> viewAllUser();
	
	public List<String> searchId(String partialUserId);
	
	public String resetPassword(String userId, String newPassword);

}
