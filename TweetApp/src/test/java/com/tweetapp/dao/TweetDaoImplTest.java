package com.tweetapp.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.tweetapp.entity.TweetTable;
import com.tweetapp.exception.NotValidException;
import com.tweetapp.repository.TweetInfoRepository;
import com.tweetapp.repository.UserInfoRepostitory;




@SpringBootTest
public class TweetDaoImplTest {
	
	@InjectMocks
	TweetDaoImpl tweetDaoImpl;
	
	@Mock
	TweetInfoRepository tweetInfoRepository;
	
	@Mock
	TweetTable tweetTable;
	
	@Mock
	UserInfoRepostitory userInfoRepository;
	
	private TweetTable newTweet() {
		tweetTable.setTweetId(1L);
		tweetTable.setUserId("test");
		tweetTable.setRootTweet("New Tweet");
		return tweetTable;
	}
	
	@Test
	public void testUserId() {
		when(userInfoRepository.searchUserId("tst")).thenReturn(new ArrayList<>());
		assertEquals(new ArrayList<>(),tweetDaoImpl.searchId("test"));
	}
}
