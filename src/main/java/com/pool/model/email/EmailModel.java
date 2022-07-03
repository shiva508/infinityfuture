package com.pool.model.email;

public class EmailModel {
	private String userName;
	private String email;
	private String password;

	public EmailModel(String userName, String email, String password) {
		super();
		this.userName = userName;
		this.email = email;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

}
