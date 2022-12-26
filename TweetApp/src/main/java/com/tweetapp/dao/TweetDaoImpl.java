package com.tweetapp.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.tweetapp.config.JwtTokenUtil;
import com.tweetapp.entity.ReplyTweetTable;
import com.tweetapp.entity.TweetTable;
import com.tweetapp.exception.NotValidException;
import com.tweetapp.repository.ReplyTweetInfoRepository;
import com.tweetapp.repository.TweetInfoRepository;
import com.tweetapp.repository.UserInfoRepostitory;

@Service
public class TweetDaoImpl implements TweetDao {

	@Autowired
	TweetInfoRepository tweetInfoRepository;
	
	@Autowired
	UserInfoRepostitory userInfoRepostitory;

	@Autowired
	ReplyTweetInfoRepository replyTweetInfoRepository;

	
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private KafkaTemplate<String, Object> template;
	
	private String topic = "posttweet";
	
	@Override
	public String postTweet(TweetTable tweetData) throws NotValidException {
		
			tweetInfoRepository.save(tweetData);
			//template.send(topic, tweetData);
			return "Tweet Successfully Created";
	}

	@Override
	public ArrayList<TweetTable> getUserTweets(String userId) throws NotValidException {

		ArrayList<TweetTable> tweetData = new ArrayList<>();
		List<TweetTable> tweetTableData = tweetInfoRepository.getByUserId(userId);
		tweetTableData.forEach(i->{
			List<ReplyTweetTable> replyTweetTableData = replyTweetInfoRepository.getByTweetId(i.getTweetId());
			i.setReplytweetTable(replyTweetTableData);
			tweetData.add(i);
			});
		Collections.reverse(tweetData);
		return tweetData;
	}

	@Override
	public ArrayList<TweetTable> getAllTweets() throws NotValidException {

		ArrayList<TweetTable> allTweetData = new ArrayList<>();
		List<TweetTable> tweetTableData = tweetInfoRepository.findAll();
		tweetTableData.forEach(i->{
			List<ReplyTweetTable> replyTweetTableData = replyTweetInfoRepository.getByTweetId(i.getTweetId());
			i.setReplytweetTable(replyTweetTableData);
			allTweetData.add(i);
		});
		Collections.reverse(allTweetData);
		return allTweetData;
	}

	@Override
	public String updateByTweeId(TweetTable tweetTable) throws NotValidException {

		TweetTable t = tweetInfoRepository.getByTweetId(tweetTable.getTweetId());

		if (t.getUserId().equals(tweetTable.getUserId()) && t.getTweetId().equals(tweetTable.getTweetId())) {
			tweetInfoRepository.updateByTweetId(tweetTable.getRootTweet(), tweetTable.getTweetId());
			return "Tweet Updated Successfully!!!";

		}

		return "Tweet Updating failed, please check with headers or the input";
	}

	@Override
	public String deleteTweet(String userId, Long tweetId) throws NotValidException {
		TweetTable t = tweetInfoRepository.getByTweetId(tweetId);
		if (t.getUserId().equals(userId) && t.getTweetId().equals(tweetId)) {
			tweetInfoRepository.deleteByTweetId(tweetId);
			replyTweetInfoRepository.deleteByTweetId(tweetId);
			return "Tweet deleted successfully!!!";
		}
		return "Unable to delete tweet, please check your inputs";

	}

	@Override
	public String likeTweet(String userId, Long tweetId) throws NotValidException {
		TweetTable t = tweetInfoRepository.getByTweetId(tweetId);
		Long count = 0L;
		if (t.getLikeCount() != null) {
			count = t.getLikeCount();
		}
		String lUser = t.getLikeUser();

		if (lUser == null||lUser.isEmpty()) {
			count++;
			tweetInfoRepository.UpdateLikeByTweetId(count, userId, tweetId);
			return "Like updated";
		} else if (lUser != null) {
			List<String> user = new ArrayList<>(Arrays.asList(lUser.split(", ")));
			for (String i : user) {
				if (i.equals(userId)) {
					user.remove(i);
					count--;
					String joined = user.stream().map(Object::toString).collect(Collectors.joining(", "));
					System.out.println("Joined "+joined);
					tweetInfoRepository.UpdateLikeByTweetId(count, joined, tweetId);
					return "Dislike updated";
				}
			}
			count++;
			user.add(userId);
			String joined =user.stream().map(Object::toString).collect(Collectors.joining(", "));
			System.out.println("Joined "+joined);
			tweetInfoRepository.UpdateLikeByTweetId(count, joined, tweetId);

		}
		return "Like updated";

	}
	
	@Override
	public String replyTweet(ReplyTweetTable replyTweetTable)throws NotValidException {
		if(replyTweetTable!=null&&tweetInfoRepository.getByTweetId(replyTweetTable.getTweetId())!=null) {
			replyTweetInfoRepository.save(replyTweetTable);
			return "Reply Updated Successfully!!!";
		}
		return "Reply Tweet was not a valid data";
	}

	@Override
	public List<String> viewAllUser() throws NotValidException {
		
		return userInfoRepostitory.findAllUser();
	}

	@Override
	public List<String> searchId(String partialUserId) throws NotValidException {
		System.out.println("search");
		return userInfoRepostitory.searchUserId(partialUserId);
	}

	@Override
	public String resetPassword(String userId, String newPassword) throws NotValidException {
		if(userInfoRepostitory.notInUserId(userId)!=null) {
			userInfoRepostitory.updatePassword(newPassword, userId);
			return "Password updated successfully";
		}
		return "userId not exist in DB";
	}

	@Override
	public List<ReplyTweetTable> getRelyTweet(Long tweetId) {
		ArrayList<ReplyTweetTable> replies = new ArrayList<>();
		if(tweetId!=null) {
			replies = replyTweetInfoRepository.getByTweetId(tweetId);
		}
		return replies;
	}
	

}
