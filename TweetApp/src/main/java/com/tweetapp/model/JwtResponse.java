package com.tweetapp.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {
	
	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getJwttoken() {
		return jwttoken;
	}
	@Override
	public String toString() {
		return "JwtResponse [jwttoken=" + jwttoken + "]";
	}
	public JwtResponse(String jwttoken) {
		super();
		this.jwttoken = jwttoken;
	}


}
