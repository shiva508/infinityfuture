package com.pool.config.exception;

import java.sql.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.pool.model.exception.EmailExistException;
import com.pool.model.exception.response.CustomErrorResponse;

@RestControllerAdvice
public class InfinityFutureExceptionHandler {

	@ExceptionHandler(value = EmailExistException.class)
	public ResponseEntity<CustomErrorResponse> handleGenericNotFoundException(EmailExistException error) {
		return new ResponseEntity<CustomErrorResponse>(
				new CustomErrorResponse().setErrorCode(HttpStatus.NOT_FOUND.toString()).setErrorMsg(error.getMessage())
						.setStatus((HttpStatus.NOT_FOUND.value())).setTimestamp(new Date(System.currentTimeMillis())),
				HttpStatus.NOT_FOUND);
	}

}
