package com.tweetapp.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTableTest {
	
	@InjectMocks
	UserTable userTable;
	
	@Test
	public void setFirstNameTest() {
		userTable.setFirstName("testing piranesh");
		assertEquals("testing piranesh", userTable.getFirstName());
	}
	
	@Test
	public void setLastNameTest() {
		userTable.setLastName("Guru");
		assertEquals("Guru",userTable.getLastName());
	}
	
	@Test
	public void setEmailIdTest() {
		userTable.setEmailId("abc@xyz.com");
		assertEquals("abc@xyz.com",userTable.getEmailId());
	}
	
	@Test
	public void setContatcNumber() {
		userTable.setContactNumber("9632587412");
		assertEquals("9632587412", userTable.getContactNumber());
	}

}
