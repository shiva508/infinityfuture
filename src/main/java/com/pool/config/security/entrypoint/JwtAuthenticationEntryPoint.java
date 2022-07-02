package com.pool.config.security.entrypoint;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pool.model.HttpResponse;
import com.pool.util.InfinityFutureSecurityConstant;

@Component
public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2)
			throws IOException {
		HttpResponse httpResponse = new HttpResponse().setHttpStatusCode(HttpStatus.FORBIDDEN.value())
				.setHttpStatus(HttpStatus.FORBIDDEN).setMessage(InfinityFutureSecurityConstant.FORBIDDEN_MESSAGE)
				.setReason(HttpStatus.FORBIDDEN.getReasonPhrase()).setTimeStamp(new Date());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.FORBIDDEN.value());

		OutputStream outputStream = response.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(outputStream, httpResponse);
		outputStream.flush();
	}
}
