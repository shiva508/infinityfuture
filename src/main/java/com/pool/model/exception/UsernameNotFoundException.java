package com.pool.model.exception;

public class UsernameNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5029359362800907063L;

	public UsernameNotFoundException(String message) {
		super(message);
	}

}
