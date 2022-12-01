package com.tweetapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tweetapp.dao.TweetDao;
import com.tweetapp.dao.UserDaoImpl;
import com.tweetapp.entity.ReplyTweetTable;
import com.tweetapp.entity.TweetTable;
import com.tweetapp.exception.NotValidException;

@SpringBootTest
public class UserControllerTest {
	
	@InjectMocks
	UserController userController;
	
	@Mock
	TweetTable tweetTable;
	
	@Mock
	TweetDao tweetDao;
	
	@Mock
	UserDaoImpl userDaoImpl;
	
	@Mock
	ReplyTweetTable replyTweetTable;
	
	private String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyYW0iLCJleHAiOjE2Njk2NTcyMzMsImlhdCI6MTY2OTY1NTQzM30.1qRL1kphDcsfAkUfhaw4tQCUMZZRi2SadHOS7OfEOB5il7mDZ8gI_VoZgiXz3OzdtQI4ExSiw33huYjJaJvKnw";
	
	private TweetTable newTweet() {
		tweetTable.setTweetId(1L);
		tweetTable.setUserId("test");
		tweetTable.setRootTweet("New Tweet");
		return tweetTable;
	}
	
	private ReplyTweetTable newReplyTweet() {
		replyTweetTable.setTweetId(1L);
		replyTweetTable.setUserId("ram");
		replyTweetTable.setReplytweet("This is reply tweet");
		return replyTweetTable;
	}
	
	
	@Test
	public void addTweetTest() {
		when(tweetDao.postTweet(newTweet())).thenReturn("Tweet Successfully Created");
		when(userDaoImpl.validateToken(tweetTable.getUserId(), token)).thenReturn(true);
		ResponseEntity<String> actual = userController.postNewTweet(token, newTweet());
		assertEquals("Tweet Successfully Created",actual.getBody());
	}
	
	@Test
	public void negativeAddTweetTest() {
		when(userDaoImpl.validateToken(tweetTable.getUserId(), token)).thenReturn(false);
		Exception exception = assertThrows(NotValidException.class, () -> { userController.postNewTweet(token, newTweet());});
		assertEquals("Not a Valid token",exception.getMessage());
	}
	
	@Test
	public void replyTweetTest() {
		when(tweetDao.replyTweet(newReplyTweet())).thenReturn("Reply Updated Successfully!!!");
		when(userDaoImpl.validateToken(tweetTable.getUserId(), token)).thenReturn(true);
		ResponseEntity<String> actual = userController.replyTweet(token, newReplyTweet());
		assertEquals("Reply Updated Successfully!!!",actual.getBody());
	}
	
	@Test
	public void negativeReplyTweetTest() {
		when(userDaoImpl.validateToken(tweetTable.getUserId(), token)).thenReturn(false);
		Exception exception = assertThrows(NotValidException.class, () -> { userController.replyTweet(token, newReplyTweet());});
		assertEquals("Not a Valid token",exception.getMessage());
	}
	
	@Test
	public void updateTweetTest() {
		when(tweetDao.updateByTweeId(newTweet())).thenReturn("Tweet Updated Successfully!!!");
		when(userDaoImpl.validateToken(tweetTable.getUserId(), token)).thenReturn(true);
		ResponseEntity<Object> actual = userController.updateTweet(token, newTweet());
		assertEquals("Tweet Updated Successfully!!!",actual.getBody());
	}
	
	@Test
	public void negativeUpdateTweetTest() {
		when(userDaoImpl.validateToken(tweetTable.getUserId(), token)).thenReturn(false);
		Exception exception = assertThrows(NotValidException.class, () -> { userController.updateTweet(token, newTweet());});
		assertEquals("Not a Valid token",exception.getMessage());
	}
	
	@Test
	public void deleteTweetTest() {
		when(tweetDao.deleteTweet("test",1L)).thenReturn("Tweet deleted successfully!!!");
		when(userDaoImpl.validateToken("test", token)).thenReturn(true);
		ResponseEntity<Object> actual = userController.deleteTweet(token, "test", 1L);
		assertEquals("Tweet deleted successfully!!!",actual.getBody());
	}
	
	@Test
	public void negativeDeleteTweetTest() {
		when(userDaoImpl.validateToken(tweetTable.getUserId(), token)).thenReturn(false);
		Exception exception = assertThrows(NotValidException.class, () -> { userController.deleteTweet(token, "test", 1L);});
		assertEquals("Not a Valid token",exception.getMessage());
	}
	
	@Test
	public void likeTweetTest() {
		when(tweetDao.likeTweet("test",1L)).thenReturn("Like updated");
		when(userDaoImpl.validateToken("test", token)).thenReturn(true);
		ResponseEntity<String> actual = userController.likeUpdate(token, "test", 1L);
		assertEquals("Like updated",actual.getBody());
	}
	
	@Test
	public void negativeLikeTweetTest() {
		when(userDaoImpl.validateToken("test", token)).thenReturn(false);
		Exception exception = assertThrows(NotValidException.class, () -> { userController.likeUpdate(token, "test", 1L);});
		assertEquals("Not a Valid token",exception.getMessage());
	}
	
	@Test
	public void resetPasswordTest() {
		when(tweetDao.resetPassword("test","newPassword")).thenReturn("Password updated successfully");
		when(userDaoImpl.validateToken("test", token)).thenReturn(true);
		ResponseEntity<Object> actual = userController.resetUserPassword(token, "test", "newPassword");
		assertEquals("Password updated successfully",actual.getBody());
	}
	
	@Test
	public void negativeResetPasswordTest() {
		when(userDaoImpl.validateToken("test", token)).thenReturn(false);
		Exception exception = assertThrows(NotValidException.class, () -> { userController.resetUserPassword(token, "test", "newPassword");});
		assertEquals("Not a Valid token",exception.getMessage());
	}
	
	@Test
	public void getUserTweetTest() {
		when(tweetDao.getUserTweets("test")).thenReturn(new ArrayList<TweetTable>() );
		when(userDaoImpl.validateToken("test", token)).thenReturn(true);
		ResponseEntity<Object> actual = userController.getALLTweet(token, "test");
		assertEquals(HttpStatus.OK, actual.getStatusCode());
	}
	
	@Test
	public void negativeGetUserTweetTest() {
		when(userDaoImpl.validateToken("test", token)).thenReturn(false);
		Exception exception = assertThrows(NotValidException.class, () -> {userController.getALLTweet(token, "test");});
		assertEquals("Not a Valid token",exception.getMessage());
	}
	
	@Test
	public void getAllTweetTest() {
		when(tweetDao.getUserTweets("test")).thenReturn(new ArrayList<TweetTable>() );
		when(userDaoImpl.validateToken("test", token)).thenReturn(true);
		ResponseEntity<Object> actual = userController.getUserTweet(token, "test");
		assertEquals(HttpStatus.OK, actual.getStatusCode());
	}
	
	@Test
	public void negativeGetAllTweetTest() {
		when(userDaoImpl.validateToken("test", token)).thenReturn(false);
		Exception exception = assertThrows(NotValidException.class, () -> { userController.getUserTweet(token, "test");});
		assertEquals("Not a Valid token",exception.getMessage());
	}
	
	@Test
	public void logOutTest() {
		when(userDaoImpl.validateToken("test", token)).thenReturn(true);
		when(userDaoImpl.updateLastLogOut("test")).thenReturn("Successfully Logged out!!!");
		ResponseEntity<Object> actual = userController.logOutUser(token, "test");
		assertEquals(HttpStatus.OK,actual.getStatusCode());
	}
	
	@Test
	public void negativeLogOutTest() {
		when(userDaoImpl.validateToken("test", token)).thenReturn(false);
		Exception exception = assertThrows(NotValidException.class, () -> { userController.logOutUser(token, "test");});
		assertEquals("Not a Valid token",exception.getMessage());
	}
	
	@Test
	public void searchUserId() {
		when(userDaoImpl.validateToken("xxx", token)).thenReturn(true);
		when(tweetDao.searchId("test")).thenReturn(new ArrayList<>());
		ResponseEntity<Object> actual = userController.searchUserId(token, "test", "xxx");
		assertEquals(HttpStatus.OK,actual.getStatusCode());
	}
	
	@Test
	public void negativeSearchUserId() {
		when(userDaoImpl.validateToken("test", token)).thenReturn(false);
		Exception exception = assertThrows(NotValidException.class, () -> { userController.searchUserId(token, "test", "xxx");});
		assertEquals("Not a Valid token",exception.getMessage());
	}
	
	@Test
	public void getAllUsers() {
		when(userDaoImpl.validateToken("test", token)).thenReturn(true);
		when(tweetDao.viewAllUser()).thenReturn(new ArrayList<>());
		ResponseEntity<Object> actual = userController.getAllUsers(token, "test");
		assertEquals(HttpStatus.OK,actual.getStatusCode());
	}
	
	@Test
	public void negativeGetAllUsers() {
		when(userDaoImpl.validateToken("test", token)).thenReturn(false);
		Exception exception = assertThrows(NotValidException.class, () -> { userController.getAllUsers(token, "test");});
		assertEquals("Not a Valid token",exception.getMessage());
	}

}
