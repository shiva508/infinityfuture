package com.pool.model;

import org.springframework.http.HttpStatus;

public class HttpResponse {
	private Integer httpStatusCode;
	private HttpStatus httpStatus;
	private String reason;
	private String message;
	public HttpResponse() {
		
	}
	public Integer getHttpStatusCode() {
		return httpStatusCode;
	}
	public HttpResponse setHttpStatusCode(Integer httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
		return this;
	}
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public HttpResponse setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
		return this;
	}
	public String getReason() {
		return reason;
	}
	public HttpResponse setReason(String reason) {
		this.reason = reason;
		return this;
	}
	public String getMessage() {
		return message;
	}
	public HttpResponse setMessage(String message) {
		this.message = message;
		return this;
	}
	
	
}
