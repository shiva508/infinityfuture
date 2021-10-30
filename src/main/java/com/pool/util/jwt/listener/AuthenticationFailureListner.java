package com.pool.util.jwt.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import com.pool.service.cache.LoginAttemptService;

@Component
public class AuthenticationFailureListner {

	@Autowired
	private LoginAttemptService loginAttemptService;
	
	@EventListener
	public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent credentialsEvent) {
		Object userName=credentialsEvent.getAuthentication().getPrincipal();
		if (userName instanceof String) {
			String username = (String) userName;
			
		}
	}

}
