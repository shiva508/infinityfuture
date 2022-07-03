package com.pool.config.exception;

import java.sql.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.pool.model.exception.EmailExistException;
import com.pool.model.exception.response.CustomErrorResponse;

@RestControllerAdvice
public class InfinityFutureExceptionHandler {

	@ExceptionHandler(value = EmailExistException.class)
	public ResponseEntity<CustomErrorResponse> handleGenericNotFoundException(EmailExistException error) {
		String errorCode = HttpStatus.NOT_FOUND.toString();
		Integer status = HttpStatus.NOT_FOUND.value();

		return commonException(error.getMessage(), errorCode, status, HttpStatus.NOT_FOUND);
	}

	private ResponseEntity<CustomErrorResponse> commonException(String error, String errorCode,
			Integer status, HttpStatus notFound) {
		return new ResponseEntity<CustomErrorResponse>(new CustomErrorResponse().setErrorCode(errorCode)
				.setErrorMsg(error).setStatus(status).setTimestamp(new Date(System.currentTimeMillis())),
				notFound);
	}
	
	@ExceptionHandler(value = NoHandlerFoundException.class)
	public ResponseEntity<CustomErrorResponse> handleHandlerNotFoundException(NoHandlerFoundException error) {
		String errorCode = HttpStatus.BAD_REQUEST.toString();
		Integer status = HttpStatus.BAD_REQUEST.value();

		return commonException(error.getMessage(), errorCode, status, HttpStatus.BAD_REQUEST);
	}

}
