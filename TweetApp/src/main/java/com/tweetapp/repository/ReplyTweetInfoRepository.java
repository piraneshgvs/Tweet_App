package com.tweetapp.repository;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tweetapp.entity.ReplyTweetTable;

public interface ReplyTweetInfoRepository extends JpaRepository<ReplyTweetTable, Long>{
	
   public ArrayList<ReplyTweetTable> getByTweetId(Long tweeId);
   
   @Modifying
   @Transactional
   @Query(value="delete from reply_tweet_info where tweet_id=?",nativeQuery = true)
   public void deleteByTweetId(Long tweetId);

}
