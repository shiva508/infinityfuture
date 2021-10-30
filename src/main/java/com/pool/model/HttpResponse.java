package com.pool.model;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class HttpResponse {
	
	private Integer httpStatusCode;
	private HttpStatus httpStatus;
	private String reason;
	private String message;
	@JsonFormat(shape = Shape.STRING,pattern = "MM-dd-yyyy hh:mm:ss")
	private Date timeStamp;

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

	public Date getTimeStamp() {
		return timeStamp;
	}

	public HttpResponse setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
		return this;
	}

}
