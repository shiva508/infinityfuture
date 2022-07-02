package com.pool.model.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8873770364745658283L;

	public UserNotFoundException(String message) {
		super(message);

	}

}
