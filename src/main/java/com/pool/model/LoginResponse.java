package com.pool.model;

public class LoginResponse {
	private String userName;
	private String jwtToken;

	private String firstName;

	public LoginResponse() {

	}

	public String getUserName() {
		return userName;
	}

	public LoginResponse setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public LoginResponse setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public LoginResponse setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

}
