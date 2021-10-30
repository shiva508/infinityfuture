package com.pool.util.jwt.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.pool.domain.User;
import com.pool.service.cache.LoginAttemptService;

@Component
public class AuthenticationScucessListner {
	@Autowired
	private LoginAttemptService loginAttemptService;
	
	@EventListener
	public void onAuthenticationSuccess(AuthenticationSuccessEvent successEvent) {
		Object principal=successEvent.getAuthentication().getPrincipal();
		if (principal instanceof User) {
			String new_name = ((User) principal).getUsername();
			loginAttemptService.evictLoggedUser(new_name);
		}
	}
}
