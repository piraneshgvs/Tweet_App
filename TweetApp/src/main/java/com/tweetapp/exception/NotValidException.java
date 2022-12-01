package com.tweetapp.exception;



public class NotValidException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public NotValidException(String message) {
		super(message);
	}


}
