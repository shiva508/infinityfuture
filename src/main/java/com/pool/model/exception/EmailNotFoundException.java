package com.pool.model.exception;

public class EmailNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1847516600299490902L;

	public EmailNotFoundException(String message) {
		super(message);
	}
}
