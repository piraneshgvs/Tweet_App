package com.tweetapp.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tweetapp.entity.TweetTable;

@Repository
public interface TweetInfoRepository extends JpaRepository<TweetTable, Long> {
	
	
	public List<TweetTable> getByUserId(String userId);
	
	@Modifying
	@Transactional
	@Query(value="update tweet_info set root_tweet= :updateTweet where tweet_id= :tweetId", nativeQuery = true)
	public void updateByTweetId(@Param("updateTweet") String updateTweet, @Param("tweetId") Long tweetId);
	
	@Query(value="select * from tweet_info where tweet_id=?",nativeQuery = true)
	public TweetTable getByTweetId(Long tweeId);
	
	@Modifying
	@Transactional
	@Query(value="delete from tweet_info where tweet_id=:tweetId",nativeQuery = true)
	public void deleteByTweetId(@Param("tweetId") Long tweetId);
	
	@Modifying
	@Transactional
	@Query(value="update tweet_info set like_count= :count, like_user= :likeUser where tweet_id= :tweetId", nativeQuery = true)
	public void UpdateLikeByTweetId(@Param("count") Long count, @Param("likeUser") String likeUser ,@Param("tweetId") Long tweetId);
	
}
