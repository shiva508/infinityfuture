package com.pool.model.exception;

public class EmailExistException extends RuntimeException {

	private static final long serialVersionUID = 3075479198970726381L;

	public EmailExistException(String message) {
		super(message);
	}

}
